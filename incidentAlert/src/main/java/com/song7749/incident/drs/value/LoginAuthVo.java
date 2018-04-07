package com.song7749.incident.drs.value;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;

import com.song7749.base.AbstractVo;
import com.song7749.incident.drs.domain.Member;
import com.song7749.incident.drs.type.AuthType;

/**
 * <pre>
 * Class Name : LoginAuthVo.java
 * Description : 회원 인증 정보 객체
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 3. 9.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 3. 9.
*/
public class LoginAuthVo extends AbstractVo {

	private static final long serialVersionUID = -4016069181069163117L;

	private Long id;

	private String loginId;

	private String ip;

	private AuthType authType;

	@DateTimeFormat(pattern = "yyyy-MM-dd h:i:s")
	private Date sessionCreateDate;

	public LoginAuthVo() {}

	/**
	 * @param id
	 */
	public LoginAuthVo(Long id) {
		this.id = id;
	}

	/**
	 * @param id
	 * @param authType
	 */
	public LoginAuthVo(Long id, AuthType authType) {
		this.id = id;
		this.authType = authType;
	}

	/**
	 * @param loginId
	 * @param authType
	 */
	public LoginAuthVo(String loginId, AuthType authType) {
		this.loginId = loginId;
		this.authType = authType;
	}


	/**
	 * @param id
	 * @param loginId
	 * @param authType
	 * @param sessionCreateDate
	 */
	public LoginAuthVo(Long id, String loginId, AuthType authType, Date sessionCreateDate) {
		this.id = id;
		this.loginId = loginId;
		this.authType = authType;
		this.sessionCreateDate = sessionCreateDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public AuthType getAuthType() {
		if(null==authType)
			throw new IllegalArgumentException("권한 부여가 되지 않아 접근이 불가능 합니다.");

		return authType;
	}

	public void setAuthType(AuthType authType) {
		this.authType = authType;
	}

	public Date getSessionCreateDate() {
		return sessionCreateDate;
	}

	public void setSessionCreateDate(Date sessionCreateDate) {
		this.sessionCreateDate = sessionCreateDate;
	}

	public Member getMember(ModelMapper mapper) {
		return mapper.map(this, Member.class);
	}
}