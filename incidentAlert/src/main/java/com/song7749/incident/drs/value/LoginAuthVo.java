package com.song7749.incident.drs.value;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.song7749.base.AbstractVo;

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

	private String loginId;

	@DateTimeFormat(pattern = "yyyy-MM-dd h:i:s")
	private Date createDate;


	public LoginAuthVo() {}

	/**
	 * @param loginId
	 * @param createDate
	 */
	public LoginAuthVo(String loginId, Date createDate) {
		this.loginId = loginId;
		this.createDate = createDate;
	}

	public String getLoginId() {
		return loginId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}