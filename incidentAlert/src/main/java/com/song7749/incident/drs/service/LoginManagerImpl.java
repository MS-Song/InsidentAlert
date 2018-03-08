package com.song7749.incident.drs.service;

import static com.song7749.util.LogMessageFormatter.format;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.song7749.incident.annotation.Login;
import com.song7749.incident.drs.domain.Member;
import com.song7749.incident.drs.repository.MemberRepository;
import com.song7749.incident.drs.type.AuthType;
import com.song7749.incident.drs.value.LogLoginAddDto;
import com.song7749.incident.drs.value.LoginDoDTO;
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

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	MemberManager memberManager;

	@Autowired
	LogManager logManager;

	@Override
	public boolean isLogin(HttpServletRequest request) {
		return null!=getLoginID(request) ? true : false;
	}

	@Override
	@Valid
	@Transactional
	public boolean doLogin(LoginDoDTO dto,HttpServletRequest request,HttpServletResponse response){

		Member member = memberRepository.findByLoginIdAndPassword(dto.getLoginId(), dto.getPassword());
		// 회원 정보가 조회가 되면.. 회원이 존재함.
		if(null != member){
			// 로그인 cookie 정보를 생성 한다.
			Cookie ciperCookie = new Cookie(cipher,CryptoAES.encrypt(member.getLoginId()));
			ciperCookie.setMaxAge(60*60*24);
			ciperCookie.setPath("/");
			response.addCookie(ciperCookie);

			// 마지막 로그인 시간 업데이트
			memberManager.modifyMemberLastLoginDate(dto);

			// 로그인 로그 기록 -- asyc 기록
			LogLoginAddDto logLoginAddDto = new LogLoginAddDto(
					request.getRemoteAddr(),
					member.getLoginId(),
					CryptoAES.encrypt(member.getLoginId()));
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
	public String getLoginID(HttpServletRequest request) {
		String cipher = null;
		Cookie[] cookie = request.getCookies();

		// 쿠키에서 cipher 를 찾아낸다.
		if (cookie != null) {
			for (int i=0; i<cookie.length; i++) {
				if (null != cookie[i]
						&& cookie[i].getName().equals(this.cipher)) {

					cipher = cookie[i].getValue();
					// 복호화 된 ID 정보를 리턴한다.
					logger.debug(format("값 : {} , 길이 : {}","로그인 정보 복호화"),cipher,cipher.length());

					if(!StringUtils.isBlank(cipher) && cipher.length()>=24){
						logger.debug(format("{}","로그인 정보 복호화"),"복호화 성공");
						return CryptoAES.decrypt(cipher);
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
	@Override
	@Transactional(readOnly=true)
	public boolean isAccese(HttpServletRequest request,Login login) {
		// 회원 로그인 정보에서 데이터를 가져와서 권한 여부를 판단한다.
		Member member = memberRepository.findByLoginId(getLoginID(request));

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

	@Override
	public boolean isIdentification(HttpServletRequest request, String loginId) {
		return getLoginID(request) != null ? getLoginID(request).equals(loginId) : false;
	}
}