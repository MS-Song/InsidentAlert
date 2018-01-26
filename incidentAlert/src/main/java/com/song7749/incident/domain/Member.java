package com.song7749.incident.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.song7749.base.Entities;

/**
 * <pre>
 * Class Name : Member.java
 * Description : 회원 Entity
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 1. 23.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 1. 23.
*/

@Entity
public class Member extends Entities{

	private static final long serialVersionUID = 474942474313960087L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	@Email
	@Length(max=60)
	@NotBlank
	private String loginId;

	@Column(nullable = false)
	@Length(min=8,max=20)
	@NotBlank
	private String password;

	@Column(nullable = false)
	@Length(max=60)
	@NotBlank
	private String teamName;

	@Column(nullable = false)
	@Length(max=60)
	@NotBlank
	private String name;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLoginDate;


	public Member() {}

	/**
	 * @param id
	 * @param loginId
	 * @param password
	 * @param teamName
	 * @param name
	 * @param createDate
	 * @param lastLoginDate
	 */
	public Member(Long id, String loginId, String password, String teamName,
			String name, Date createDate, Date lastLoginDate) {
		this.id = id;
		this.loginId = loginId;
		this.password = password;
		this.teamName = teamName;
		this.name = name;
		this.createDate = createDate;
		this.lastLoginDate = lastLoginDate;
	}

	/**
	 * @param loginId
	 * @param password
	 * @param teamName
	 * @param name
	 * @param createDate
	 * @param lastLoginDate
	 */
	public Member(String loginId, String password, String teamName,
			String name, Date createDate, Date lastLoginDate) {
		this.loginId = loginId;
		this.password = password;
		this.teamName = teamName;
		this.name = name;
		this.createDate = createDate;
		this.lastLoginDate = lastLoginDate;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
}