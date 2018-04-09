package com.song7749.incident.web;

import static com.song7749.util.LogMessageFormatter.format;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.song7749.base.MessageVo;
import com.song7749.incident.annotation.Login;
import com.song7749.incident.drs.service.DBclienManager;
import com.song7749.incident.drs.service.DatabaseManager;
import com.song7749.incident.drs.service.MemberManager;
import com.song7749.incident.drs.session.LoginSession;
import com.song7749.incident.drs.type.AuthType;
import com.song7749.incident.drs.type.Charset;
import com.song7749.incident.drs.type.DatabaseDriver;
import com.song7749.incident.drs.value.DatabaseAddDto;
import com.song7749.incident.drs.value.DatabaseFindDto;
import com.song7749.incident.drs.value.DatabaseModifyDto;
import com.song7749.incident.drs.value.DatabaseRemoveDto;
import com.song7749.incident.drs.value.DatabaseVo;
import com.song7749.incident.drs.value.MemberDatabaseFindDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "데이터베이스 정보 관리")
@RestController
@RequestMapping("/database")
public class DatabaseController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	DatabaseManager databaseManager;

	@Autowired
	DBclienManager dbclientManager;

	@Autowired
	LoginSession session;

	@Autowired
	MemberManager memberManager;

	@PostMapping("/add")
	@ApiOperation(value = "데이터베이스 정보 입력", response = DatabaseVo.class)
	@Login({ AuthType.ADMIN })
	public MessageVo addDatabase(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute DatabaseAddDto dto) {

		logger.trace(format("{}", "addDatabase"),dto);

		return new MessageVo(HttpServletResponse.SC_OK, 1, databaseManager.addDatabase(dto), "Database 정보가 저장 되었습니다.");
	}

	@PutMapping("/modify")
	@ApiOperation(value = "데이터베이스 정보 수정", response = DatabaseVo.class)
	@Login({ AuthType.ADMIN })
	public MessageVo modifyDatabase(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute DatabaseModifyDto dto) {

		return new MessageVo(HttpServletResponse.SC_OK, 1, databaseManager.modifyDatabase(dto), "Database 정보가 저장 되었습니다.");
	}

	@DeleteMapping("/remove")
	@ApiOperation(value = "데이터베이스 정보 삭제", response = MessageVo.class)
	@Login({ AuthType.ADMIN })
	public MessageVo removeDatabase(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute DatabaseRemoveDto dto) {

		databaseManager.removeDatabase(dto);
		return new MessageVo(HttpServletResponse.SC_OK , 1, "Database 정보가 삭제 되었습니다.");
	}

	@GetMapping("/list")
	@ApiOperation(value = "데이터베이스 정보 조회", response = DatabaseVo.class)
	@Login({ AuthType.NORMAL, AuthType.ADMIN })
	public Page<DatabaseVo> getList(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute DatabaseFindDto dto,
			@PageableDefault(page=0, size=100, direction=Direction.DESC, sort="id")
			Pageable page){

		// 권한 확인 후에 관리자가 아니면 database 확인 후 조회 하도록 처리 한다.
		// 일반 회원인 경우 회원의 권한이 있는 DB만 조회 가능 하다
		if(session.getLogin().getAuthType().equals(AuthType.NORMAL)){
			return memberManager.findDatabaseListByMemberAllow(
					new MemberDatabaseFindDto(
							session.getLogin().getId()), page);
		} else {
			return databaseManager.findDatabaseList(dto, page);
		}
	}

	@GetMapping("/getDatabaseDriver")
	@ApiOperation(value = "데이터 베이스 드라이버 조회", response=DatabaseDriver.class)
	public DatabaseDriver[] getDatabaseDriver(HttpServletRequest request, HttpServletResponse response){
		return DatabaseDriver.values();
	}


	@GetMapping("/getCharset")
	@ApiOperation(value = "Charset 조회", response=Charset.class)
	public Charset[] getCharset(HttpServletRequest request, HttpServletResponse response){
		return Charset.values();
	}
}