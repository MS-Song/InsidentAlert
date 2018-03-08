package com.song7749.incident.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.song7749.incident.drs.domain.Database;
import com.song7749.incident.drs.service.DatabaseManager;
import com.song7749.incident.drs.value.DatabaseAddDto;
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

	@ApiOperation(value = "데이터베이스 정보 입력", response = Database.class)
	@PostMapping("/add")
	@ResponseBody
	public DatabaseVo addDatabase(
			HttpServletRequest request,
			HttpServletResponse response,
			@Valid @ModelAttribute DatabaseAddDto dto) {
		return databaseManager.addDatabase(dto);
	}

	@PutMapping("/modify")
	public void modifyDatabase() {

	}

	@DeleteMapping("/delete")
	public void deleteDatabase() {

	}

	@GetMapping("/list")
	public String list() {
		return "index";
	}

}