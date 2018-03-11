package com.song7749.incident.drs.type;

import com.song7749.incident.drs.value.MemberModifyDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <pre>
 * Class Name : MemberModifyByAdminDto.java
 * Description : 관리자 회원 수정
 * 일반 회원은 본인 회원 정보만 수정 가능하나, 관리자는 다른 회원의 정보 외 추가의 권한 부여가 가능하다.
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

@ApiModel("관리자 회원 수정 객체")
public class MemberModifyByAdminDto extends MemberModifyDto {

	private static final long serialVersionUID = -595290423324771739L;

	@ApiModelProperty("권한")
	private AuthType authType;

	public MemberModifyByAdminDto() {}

	/**
	 * @param id
	 * @param changeCertificationKey
	 * @param password
	 * @param passwordQuestion
	 * @param passwordAnswer
	 * @param teamName
	 * @param name
	 */
	public MemberModifyByAdminDto(Long id, Boolean changeCertificationKey, String password, String passwordQuestion,
			String passwordAnswer, String teamName, String name, AuthType authType) {
		super(id, changeCertificationKey, password, passwordQuestion, passwordAnswer, teamName, name);
		this.authType=authType;
	}

	public AuthType getAuthType() {
		return authType;
	}

	public void setAuthType(AuthType authType) {
		this.authType = authType;
	}
}
