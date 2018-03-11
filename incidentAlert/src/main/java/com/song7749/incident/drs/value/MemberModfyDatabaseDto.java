package com.song7749.incident.drs.value;

import javax.validation.constraints.NotNull;

import com.song7749.base.BaseObject;
import com.song7749.base.Dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <pre>
 * Class Name : MemberModfyDatabaseDto.java
 * Description : 회원과 Database 간의 연결 수정
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 3. 10.		song7749@gmail.com		NEW
 *
 * </pre>
 *
 * @author song7749@gmail.com
 * @since 2018. 3. 10.
 */
@ApiModel("회원 데이터 베이스 권한 부여")
public class MemberModfyDatabaseDto extends BaseObject implements Dto {

	private static final long serialVersionUID = -6283249657510420187L;

	@ApiModelProperty(required=false,value="회원별 데이터베이스 권한 ID"
			, notes="값이 입력될 경우 해당 권한이 삭제되고 , null 경우 권한이 생성된다.")
	private Long memberDatabaseId;

	@ApiModelProperty(required = true, value = "데이터베이스ID")
	@NotNull
	private Long datebaseId;

	@ApiModelProperty(required = true, value = "회원ID")
	@NotNull
	private Long memberId;

	public MemberModfyDatabaseDto() {}

	/**
	 * @param memberDatabaseId
	 */
	public MemberModfyDatabaseDto(Long memberDatabaseId) {
		this.memberDatabaseId = memberDatabaseId;
	}

	/**
	 * @param datebaseId
	 * @param memberId
	 */
	public MemberModfyDatabaseDto(@NotNull Long datebaseId, @NotNull Long memberId) {
		super();
		this.datebaseId = datebaseId;
		this.memberId = memberId;
	}

	/**
	 * @param memberDatabaseId
	 * @param datebaseId
	 * @param memberId
	 */
	public MemberModfyDatabaseDto(Long memberDatabaseId, @NotNull Long datebaseId, @NotNull Long memberId) {
		super();
		this.memberDatabaseId = memberDatabaseId;
		this.datebaseId = datebaseId;
		this.memberId = memberId;
	}

	public Long getMemberDatabaseId() {
		return memberDatabaseId;
	}

	public Long getDatebaseId() {
		return datebaseId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberDatabaseId(Long memberDatabaseId) {
		this.memberDatabaseId = memberDatabaseId;
	}

	public void setDatebaseId(Long datebaseId) {
		this.datebaseId = datebaseId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
}