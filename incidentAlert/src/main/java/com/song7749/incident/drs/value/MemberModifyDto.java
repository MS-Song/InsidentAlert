package com.song7749.incident.drs.value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import com.song7749.base.BaseObject;
import com.song7749.base.Dto;
import com.song7749.incident.drs.domain.Member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("회원수정")
public class MemberModifyDto extends BaseObject implements Dto {

	private static final long serialVersionUID = -3526116343828894825L;

	@ApiModelProperty(value = "회원번호", required = true)
	@NotNull
	private Long id;

	@ApiModelProperty("인증키 변경 여부")
	private Boolean changeCertificationKey;

	@ApiModelProperty(value = "패스워드", required = true, example = "8~20 자 이내로 영문+특수문자 조합으로 입력하세요")
	@Length(min = 8, max = 20)
	private String password;

	@ApiModelProperty(value = "패스워드 찾기 질문", required = true, example = "6~120 자 로 입력하세요")
	@Size(min = 6, max = 120)
	private String passwordQuestion;

	@ApiModelProperty(value = "패스워드 찾기 답변", required = true, example = "6~120 자 로 입력하세요")
	@Size(min = 6, max = 120)
	private String passwordAnswer;

	@ApiModelProperty(value = "팀명", required = true, example = "60자 이내로 입력하세요")
	@Length(max = 60)
	private String teamName;

	@ApiModelProperty(value = "성명", required = true, example = "60자 이내로 입력하세요")
	@Length(max = 60)
	private String name;

	public MemberModifyDto() {}

	/**
	 * @param id
	 * @param changeCertificationKey
	 * @param password
	 * @param passwordQuestion
	 * @param passwordAnswer
	 * @param teamName
	 * @param name
	 */
	public MemberModifyDto(@NotNull Long id, Boolean changeCertificationKey,
			@Length(min = 8, max = 20) String password, @Size(min = 6, max = 120) String passwordQuestion,
			@Size(min = 6, max = 120) String passwordAnswer, @Length(max = 60) String teamName,
			@Length(max = 60) String name) {
		this.id = id;
		this.changeCertificationKey = changeCertificationKey;
		this.password = password;
		this.passwordQuestion = passwordQuestion;
		this.passwordAnswer = passwordAnswer;
		this.teamName = teamName;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getChangeCertificationKey() {
		return changeCertificationKey;
	}

	public void setChangeCertificationKey(Boolean changeCertificationKey) {
		this.changeCertificationKey = changeCertificationKey;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordQuestion() {
		return passwordQuestion;
	}

	public void setPasswordQuestion(String passwordQuestion) {
		this.passwordQuestion = passwordQuestion;
	}

	public String getPasswordAnswer() {
		return passwordAnswer;
	}

	public void setPasswordAnswer(String passwordAnswer) {
		this.passwordAnswer = passwordAnswer;
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

	public Member getMemeber(ModelMapper mapper) {
		return mapper.map(this, Member.class);
	}
}