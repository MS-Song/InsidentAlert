package com.song7749.incident.drs.service;

import static com.song7749.util.LogMessageFormatter.format;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.song7749.incident.annotation.Login;
import com.song7749.incident.drs.domain.Member;
import com.song7749.incident.drs.repository.MemberRepository;
import com.song7749.incident.drs.type.AuthType;
import com.song7749.incident.drs.value.LogLoginAddDto;
import com.song7749.incident.drs.value.LoginAuthVo;
import com.song7749.incident.drs.value.LoginDoDto;
import com.song7749.util.ObjectJsonUtil;
import com.song7749.util.StringUtils;
import com.song7749.util.crypto.CryptoAES;


/**
 * <pre>
 * Class Name : LoginManagerImpl.java
 * Description : 로그인 매니저 구현체
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 2. 28.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 2. 28.
*/
@Service("loginManager")
public class LoginManagerImpl implements LoginManager{

	Logger logger = LoggerFactory.getLogger(getClass());

	// 로그인 정보 저장 쿠키 명칭
	private final String cipher = "cipher";

	@Value("${app.login.session.timeout}")
	private Integer sessionTimeout;

	@Value("${app.login.session.checktime}")
	private Integer sessionChecktime;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	MemberManager memberManager;

	@Autowired
	LogManager logManager;

	@Override
	public boolean isLogin(HttpServletRequest request, HttpServletResponse response) {
		return null!=getLoginID(request,response) ? true : false;
	}

	@Override
	@Valid
	@Transactional
	public boolean doLogin(LoginDoDto dto,HttpServletRequest request, HttpServletResponse response){

		Member member = memberRepository.findByLoginIdAndPassword(dto.getLoginId(), dto.getPassword());
		// 회원 정보가 조회가 되면.. 회원이 존재함.
		if(null != member){
			// 로그인 cookie 정보를 생성 한다.
			LoginAuthVo lav = new LoginAuthVo(member.getLoginId(), new Date(System.currentTimeMillis()));
			String cipherValue = null;
			try {
				cipherValue=CryptoAES.encrypt(ObjectJsonUtil.getJsonStringByObject(lav));
			} catch (Exception e) {
				throw new IllegalArgumentException("회원 인증 생성 실패. 관리자에게 문의 하세요");
			}

			Cookie cipherCookie = new Cookie(cipher,cipherValue);
			cipherCookie.setMaxAge(60*this.sessionTimeout);
			cipherCookie.setPath("/");
			response.addCookie(cipherCookie);

			// 마지막 로그인 시간 업데이트
			member.setLastLoginDate(new Date(System.currentTimeMillis()));
			memberRepository.saveAndFlush(member);

			// 로그인 로그 기록 -- asyc 기록
			LogLoginAddDto logLoginAddDto = new LogLoginAddDto(
					request.getRemoteAddr(),
					member.getLoginId(),
					cipherValue);
			logManager.addLogLogin(logLoginAddDto);

			logger.debug(format("{}","Login Log"),logLoginAddDto);

			return true;
		}
		throw new IllegalArgumentException("id=ID 또는 패스워드가 틀립니다.");
	}

	@Override
	public void doLogout(HttpServletResponse response) {
		Cookie cookie = new Cookie(cipher,"");
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	@Override
	public String getLoginID(HttpServletRequest request, HttpServletResponse response) {
		String cipherValue = null;
		Cookie[] cookie = request.getCookies();

		// 쿠키에서 cipher 를 찾아낸다.
		if (cookie != null) {
			for (int i=0; i<cookie.length; i++) {
				if (null != cookie[i]
						&& cookie[i].getName().equals(this.cipher)) {

					cipherValue = cookie[i].getValue();
					// 복호화 된 ID 정보를 리턴한다.
					logger.debug(format("value : {} , size : {}","login cookie inifo"),cipherValue,cipherValue.length());

					if(!StringUtils.isBlank(cipherValue) && cipherValue.length()>=24){
						LoginAuthVo lav = null;
						try {
							lav = (LoginAuthVo) ObjectJsonUtil.getObjectByJsonString(CryptoAES.decrypt(cipherValue),LoginAuthVo.class);
							logger.debug(format("{}","Login Cookie 복호화 완료"),lav);
						} catch (Exception e) {
							throw new IllegalArgumentException("로그인 정보 복호화 실패. 관리자에게 문의 하시기 바랍니다.");
						}
						// session 갱신 시간 이후인 경우 DB의 인증 생성 시간과 비교 한다.
						if(lav.getCreateDate().before(new Date(System.currentTimeMillis() - sessionChecktime*1000))) {
							// TODO 로그인 기록을 확인하여 기록이 정상 적이라면 cookie 의 시간을 갱신 한다.
							lav.setCreateDate(new Date(System.currentTimeMillis()));
							logger.trace(format("{}", "session 갱신"),lav);

						}
						return lav.getLoginId();
					}
				}
			}
		}
		return null;
	}

	/**
	 * 해당 회원이 접근 가능한 기능인가 검증
	 * @param member
	 * @param login
	 * @return boolean
	 */
	@Transactional(readOnly=true)
	@Override
	public boolean isAccese(HttpServletRequest request, HttpServletResponse response, Login login) {
		// 회원 로그인 정보에서 데이터를 가져와서 권한 여부를 판단한다.
		Member member = memberRepository.findByLoginId(getLoginID(request,response));

		// 회원이 아닌 경우 권한이 없다 - 모든 페이지를 작동 불능으로 처리한다.
		if(null!=member && null!=member.getAuthType()){
			for(AuthType at : login.value()){
				if(member.getAuthType().equals(at)){
					return true;
				}
			}
		}
		return false;
	}

	@Transactional(readOnly=true)
	@Override
	public boolean isIdentification(HttpServletRequest request, HttpServletResponse response, Long id) {
		return isIdentification(request, response, memberRepository.findById(id).get().getLoginId());
	}

	@Override
	public boolean isIdentification(HttpServletRequest request, HttpServletResponse response, String loginId) {
		String rLoginId = getLoginID(request, response);
		return  rLoginId != null ? rLoginId.equals(loginId) : false;
	}
}