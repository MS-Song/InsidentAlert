package com.song7749.incident.drs.service;

import java.util.List;

import com.song7749.incident.drs.type.MemberModifyByAdminDto;
import com.song7749.incident.drs.value.MemberAddDto;
import com.song7749.incident.drs.value.MemberFindDto;
import com.song7749.incident.drs.value.MemberModfyDatabaseDto;
import com.song7749.incident.drs.value.MemberModifyDto;
import com.song7749.incident.drs.value.MemberVo;

/**
 * <pre>
 * Class Name : MemberManager.java
 * Description : Member Manager
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 2. 26.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 2. 26.
*/
public interface MemberManager {

	/**
	 * Member 추가, loginId 에 대한 중복 방지 기능이 있음.
	 * @param dto
	 * @return MemberVo
	 */
	MemberVo addMemeber(MemberAddDto dto);

	/**
	 * 회원 정보 수정
	 * @param dto
	 * @return MemberVo
	 */
	MemberVo modifyMember(MemberModifyDto dto);

	/**
	 * 관리자 회원 수정 기능
	 * @param dto
	 * @return MemberVo
	 */
	MemberVo modifyMember(MemberModifyByAdminDto dto);

	/**
	 * 회원 Database 권한 수정
	 * @param dto
	 * @return MemberVo
	 */
	MemberVo modifyMember(MemberModfyDatabaseDto dto);

	/**
	 * 회원 삭제
	 * @param id
	 */
	void removeMember(Long id);

	MemberVo findMember(Long id);

	MemberVo findMember(String loginId);

	MemberVo findMember(String loginId, String password);

	/**
	 * MemberVo 검색
	 * @param dto
	 * @return List<MemberVo>
	 */
	List<MemberVo> findMemberList(MemberFindDto dto);

}
