package com.song7749.incident.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.song7749.base.MessageVo;
import com.song7749.incident.annotation.Login;
import com.song7749.incident.drs.service.LoginManager;
import com.song7749.incident.drs.service.MemberManager;
import com.song7749.incident.drs.session.LoginSession;
import com.song7749.incident.drs.type.AuthType;
import com.song7749.incident.drs.type.LoginResponseType;
import com.song7749.incident.drs.type.MemberModifyByAdminDto;
import com.song7749.incident.drs.value.MemberAddDto;
import com.song7749.incident.drs.value.MemberDatabaseAddOrModifyDto;
import com.song7749.incident.drs.value.MemberDatabaseFindDto;
import com.song7749.incident.drs.value.MemberDatabaseVo;
import com.song7749.incident.drs.value.MemberFindDto;
import com.song7749.incident.drs.value.MemberModifyDto;
import com.song7749.incident.drs.value.MemberSaveQueryAddDto;
import com.song7749.incident.drs.value.MemberSaveQueryFindDto;
import com.song7749.incident.drs.value.MemberSaveQueryRemoveDto;
import com.song7749.incident.drs.value.MemberSaveQueryVo;
import com.song7749.incident.drs.value.MemberVo;
import com.song7749.incident.drs.value.RenewApikeyDto;
import com.song7749.incident.exception.AuthorityUserException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;




/**
 * <pre>
 * Class Name : MemberController.java
 * Description : 회원 정보 관리 컨트롤러
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

@Api(tags="회원 관리 기능")
@RestController
@RequestMapping("/member")
public class MemberController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	MemberManager memberManager;

	@Autowired
	LoginManager loginManager;

	@Autowired
	LoginSession session;

	@ApiOperation(value = "회원가입"
			,notes = "회원 정보를 등록한다. 회원 가입 시에 권한 정보는 추후 승인하는 형태로 진행 한다."
			,response=MessageVo.class)
	@PostMapping("/add")
	public MessageVo addMember(
			HttpServletRequest request,
			HttpServletResponse response,
			@Valid @ModelAttribute MemberAddDto dto){

		return new MessageVo(HttpStatus.OK.value(), 1
				, memberManager.addMemeber(dto), "회원 등록이 완료되었습니다.");
	}

	@ApiOperation(value = "회원수정"
			,notes = "회원 정보를 수정한다. 본인의 정보만 수정 가능하다."
			,response=MessageVo.class)
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	@PutMapping("/modify")
	public MessageVo modifyMember(
			HttpServletRequest request,
			HttpServletResponse response,
			@Valid @ModelAttribute MemberModifyDto dto){
		// 본인확인
		if(session.getLogin().getId().equals(dto.getId())){
			return new MessageVo(HttpStatus.OK.value(), 1
					, memberManager.modifyMember(dto), "정보 수정이 완료되었습니다.");
		} else {
			throw new AuthorityUserException("본인의 정보만 수정 가능 합니다.");
		}

	}

	@ApiOperation(value = "회원 수정 - 관리자"
			,notes = "회원 정보를 수정한다. 관리자가 수정한다."
			,response=MessageVo.class)
	@Login(AuthType.ADMIN)
	@PutMapping("/modifyByAdmin")
	public MessageVo modifyMemberByAdmin(
			HttpServletRequest request,
			HttpServletResponse response,
			@Valid @ModelAttribute MemberModifyByAdminDto dto){

		return new MessageVo(HttpStatus.OK.value(), 1
				, memberManager.modifyMember(dto), "정보 수정이 완료 되었습니다.");
	}

	@ApiOperation(value = "회원 삭제"
			, notes = "회원 정보를 삭제한다."
			, response=MessageVo.class)
	@Login(AuthType.ADMIN)
	@DeleteMapping("/remove")
	public MessageVo removeMember(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required=true) Long id){

		memberManager.removeMember(id);
		return new MessageVo(HttpStatus.OK.value(), 1, "회원이 삭제 되었습니다.");
	}

	@ApiOperation(value = "회원 조회 - 관리자"
			,notes = "회원 리스트를 조회 한다."
			,response=MemberVo.class)
	@Login({AuthType.ADMIN})
	@GetMapping("/list")
	public Page<MemberVo> listMember(
			HttpServletRequest request,
			HttpServletResponse response,
			@Valid @ModelAttribute MemberFindDto dto,
			@PageableDefault(page=0, size=20, direction=Direction.DESC, sort="id") Pageable page){

		return memberManager.findMemberList(dto,page);
	}

	@ApiOperation(value = "회원 권한 목록 조회"
			,notes = "회원 권한 목록을 조회 한다"
			,response=AuthType.class)
	@GetMapping("/getAuthTypes")
	@Login({AuthType.NORMAL, AuthType.ADMIN})
	public AuthType[] getAuthTypes(
			HttpServletRequest request,
			HttpServletResponse response){
		return AuthType.values();
	}

	@ApiOperation(value = "회원과 Database 간의 연결 추가/삭제 - 관리자"
			,notes = "회원과 Database 간의 연결을 추가 한다"
			,response=MessageVo.class)
	@PostMapping(value="/addOrModifyMemberDatabaseByAdmin")
	@Login(AuthType.ADMIN)
	public MessageVo addOrModifyMemberDatabaseByAdmin(
			HttpServletRequest request,
			HttpServletResponse response,
			@Valid @ModelAttribute MemberDatabaseAddOrModifyDto dto){

		return new MessageVo(HttpStatus.OK.value(), 1
				, memberManager.addOrModifyMemberDatabase(dto), "정보 수정이 완료 되었습니다.");
	}

	@ApiOperation(value = "회원과 Database 간의 연결 조회"
			,notes = "회원과 Database 간의 연결을 조회"
			,response=MemberDatabaseVo.class)
	@GetMapping(value="/findMemberDatabase")
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	public Page<MemberDatabaseVo> FindMemberDatabase(
			HttpServletRequest request,
			HttpServletResponse response,
			@Valid @ModelAttribute MemberDatabaseFindDto dto,
			@PageableDefault(page=0, size=100, direction=Direction.DESC, sort="id") Pageable page){

		return memberManager.findMemberDatabaseList(dto, page);
	}

	@ApiOperation(value = "API Key 관리"
			,notes = "회원의 API Key 를 발급 하거나 갱신한다."
			,response=MessageVo.class)
	@PutMapping("/renewApiKey")
	public MessageVo renewApiKey(
			HttpServletRequest request,
			HttpServletResponse response,
			RenewApikeyDto dto){
		return new MessageVo(HttpStatus.OK.value(),
				memberManager.renewApikey(dto), "apikey가 갱신 되었습니다.");
	}

	@ApiOperation(value = "API Key 관리 - 관리자"
			,notes = "회원의 API Key 를 발급 하거나 갱신한다."
			,response=MessageVo.class)
	@Login({AuthType.ADMIN})
	@PutMapping("/renewApiKeyByAdmin")
	public MessageVo renewApiKey(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required=true) String loginId){

		return new MessageVo(HttpStatus.OK.value(),
				memberManager.renewApikeyByAdmin(loginId), "apikey가 갱신 되었습니다.");
	}

	@ApiOperation(value = "회원의 쿼리 저장",notes = "자주 사용하는 쿼리를 저장하기 위한 컨트롤러"
			,response=MessageVo.class)
	@PostMapping(value="/addMemberSaveQuery")
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	public MessageVo addMemberSaveQuery(
			HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute MemberSaveQueryAddDto dto){

		// 본인 확인
		if(session.getLogin().getId().equals(dto.getMemberId())){
			return new MessageVo(HttpStatus.OK.value()
					, 1
					, memberManager.addMemberSaveQuery(dto)
					,"쿼리 데이터가 저장되었습니다.");
		} else {
			throw new AuthorityUserException("본인의 정보만 수정 가능 합니다.");
		}
	}

	@ApiOperation(value = "회원의 쿼리 삭제"
			,notes = "자주 사용하는 쿼리를 삭제하기 위한 컨트롤러"
			,response=MessageVo.class)
	@DeleteMapping(value="/removeMemberSaveQuery")
	@Login({AuthType.NORMAL,AuthType.ADMIN})
	public MessageVo removeMemberSaveQuery(
			HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute MemberSaveQueryRemoveDto dto){
		// 본인 확인
		dto.setMemberId(session.getLogin().getId());
		memberManager.removeMemberSaveQuery(dto);
		return new MessageVo(HttpStatus.OK.value()
				, 1
				,"쿼리 데이터가 삭제 되었습니다.");
	}

	@ApiOperation(value = "회원의 저장된 쿼리 조회"
			,notes = "자주 사용하는 쿼리 리스트를 조회 한다."
			,response=MemberSaveQueryVo.class)
	@RequestMapping(value="/findMemberSaveQuery",method={RequestMethod.GET,RequestMethod.POST})
	@Login(type=LoginResponseType.EXCEPTION,value={AuthType.NORMAL,AuthType.ADMIN})
	public Page<MemberSaveQueryVo> MemberSaveQuery(
			HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute MemberSaveQueryFindDto dto,
			@PageableDefault(page=0, size=100, direction=Direction.DESC, sort="id") Pageable page){

		// 본인 확인
		if(session.getLogin().getId().equals(dto.getMemberId())){
			return memberManager.findMemberSaveQueray(dto, page);
		} else {
			throw new AuthorityUserException("본인의 정보만 조회 가능 합니다.");
		}
	}
}