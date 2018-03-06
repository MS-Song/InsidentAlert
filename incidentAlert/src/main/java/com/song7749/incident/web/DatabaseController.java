package com.song7749.incident.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.spring.web.json.Json;
import springfox.documentation.spring.web.json.JsonSerializer;

@Api(tags = "데이터베이스 정보 관리")
@RestController
@RequestMapping("/database")
public class DatabaseController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	DatabaseManager databaseManager;

	@Autowired
	private JsonSerializer jsonSerializer;

	@ApiOperation(value = "데이터베이스 정보 입력", response = Database.class)
	@PostMapping("/add")
	@ResponseBody
	public ResponseEntity<Json> addDatabase(@ModelAttribute DatabaseAddDto dto, HttpServletRequest request,
			HttpServletResponse response) {

		return new ResponseEntity<Json>(jsonSerializer.toJson(databaseManager.addDatabase(dto)), HttpStatus.OK);
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