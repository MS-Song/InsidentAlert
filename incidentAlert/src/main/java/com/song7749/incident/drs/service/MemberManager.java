package com.song7749.incident.drs.service;

import java.util.List;

import com.song7749.incident.drs.value.LoginDoDTO;
import com.song7749.incident.drs.value.MemberAddDto;
import com.song7749.incident.drs.value.MemberFindDto;
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

	MemberVo addMemeber(MemberAddDto dto);

	MemberVo modifyMember(MemberModifyDto dto);

	MemberVo modifyMemberLastLoginDate(LoginDoDTO dto);

	void removeMember(Long id);

	MemberVo findMember(Long id);

	MemberVo findMember(String loginId);

	MemberVo findMember(String loginId, String password);

	/**
	 * Member VO 검색
	 * @param dto
	 * @return List<MemberVo>
	 */
	List<MemberVo> findMemberList(MemberFindDto dto);

}
