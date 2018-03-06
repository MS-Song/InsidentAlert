package com.song7749.incident.drs.value;

import com.song7749.base.AbstractFindDto;
import com.song7749.base.Compare;

/**
 * <pre>
 * Class Name : MemberFindDto.java
 * Description : Member 검색용 DTO
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 2. 27.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 2. 27.
*/

public class MemberFindDto extends AbstractFindDto {

	private static final long serialVersionUID = -111258667094644234L;

	private Long id;

	private String loginId;
	private Compare loginIdCompare = Compare.EQUAL;

	private String certificationKey;

	private String teamName;
	private Compare teamNameCompare = Compare.EQUAL;

	private String name;
	private Compare nameCompare = Compare.EQUAL;


	public MemberFindDto() {}

	/**
	 * @param id
	 * @param loginId
	 * @param loginIdCompare
	 * @param certificationKey
	 * @param teamName
	 * @param teamNameCompare
	 * @param name
	 * @param nameCompare
	 */
	public MemberFindDto(Long id, String loginId, Compare loginIdCompare, String certificationKey, String teamName,
			Compare teamNameCompare, String name, Compare nameCompare) {
		this.id = id;
		this.loginId = loginId;
		this.loginIdCompare = loginIdCompare;
		this.certificationKey = certificationKey;
		this.teamName = teamName;
		this.teamNameCompare = teamNameCompare;
		this.name = name;
		this.nameCompare = nameCompare;
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


	public Compare getLoginIdCompare() {
		return loginIdCompare;
	}


	public void setLoginIdCompare(Compare loginIdCompare) {
		this.loginIdCompare = loginIdCompare;
	}


	public String getCertificationKey() {
		return certificationKey;
	}


	public void setCertificationKey(String certificationKey) {
		this.certificationKey = certificationKey;
	}


	public String getTeamName() {
		return teamName;
	}


	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}


	public Compare getTeamNameCompare() {
		return teamNameCompare;
	}


	public void setTeamNameCompare(Compare teamNameCompare) {
		this.teamNameCompare = teamNameCompare;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Compare getNameCompare() {
		return nameCompare;
	}


	public void setNameCompare(Compare nameCompare) {
		this.nameCompare = nameCompare;
	}
}