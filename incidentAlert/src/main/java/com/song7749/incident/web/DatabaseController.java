package com.song7749.incident.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.song7749.base.MessageVo;
import com.song7749.incident.annotation.Login;
import com.song7749.incident.drs.service.DatabaseManager;
import com.song7749.incident.drs.type.AuthType;
import com.song7749.incident.drs.type.LoginResponseType;
import com.song7749.incident.drs.value.DatabaseAddDto;
import com.song7749.incident.drs.value.DatabaseModifyDto;
import com.song7749.incident.drs.value.DatabaseVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "데이터베이스 정보 관리")
@RestController
@RequestMapping("/database")
public class DatabaseController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	DatabaseManager databaseManager;

	@PostMapping("/add")
	@ApiOperation(value = "데이터베이스 정보 입력", response = DatabaseVo.class)
	@Login(type = LoginResponseType.EXCEPTION, value = { AuthType.ADMIN })
	@ResponseBody
	public MessageVo addDatabase(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute DatabaseAddDto dto) {

		return new MessageVo(HttpServletResponse.SC_OK, 1, databaseManager.addDatabase(dto), "Database 정보가 저장 되었습니다.");
	}

	@PutMapping("/modify")
	@ApiOperation(value = "데이터베이스 정보 수정", response = DatabaseVo.class)
	@Login(type = LoginResponseType.EXCEPTION, value = { AuthType.ADMIN })
	@ResponseBody
	public MessageVo modifyDatabase(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute DatabaseModifyDto dto) {

		return new MessageVo(HttpServletResponse.SC_OK, 1, databaseManager.modifyDatabase(dto), "Database 정보가 저장 되었습니다.");
	}

	@DeleteMapping("/delete")
	@ApiOperation(value = "데이터베이스 정보 삭제", response = MessageVo.class)
	@Login(type = LoginResponseType.EXCEPTION, value = { AuthType.ADMIN })
	@ResponseBody
	public MessageVo deleteDatabase(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required=true) Long databaseId) {

		databaseManager.deleteDatabase(databaseId);
		return new MessageVo(HttpServletResponse.SC_OK , 1, "Database 정보가 삭제 되었습니다.");
	}

//	@ApiOperation(value = "데이터베이스 정보 조회", response = DatabaseVo.class)
//	@Login(type = LoginResponseType.EXCEPTION, value = { AuthType.ADMIN, AuthType.NORMAL })
//	@ResponseBody
//	@GetMapping("/list")
//	public String list(HttpServletRequest request, HttpServletResponse response,
//			@Valid @ModelAttribute database dto) {
//		return "index";
//	}

}