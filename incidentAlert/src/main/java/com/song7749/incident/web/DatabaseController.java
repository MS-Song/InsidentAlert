package com.song7749.incident.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.song7749.incident.domain.Database;
import com.song7749.incident.domain.DatabaseAddDto;
import com.song7749.incident.service.DatabaseService;

@RestController
@RequestMapping("/database")
public class DatabaseController {

	@Autowired
	DatabaseService databaseService;

	@PostMapping("/add")
	@ResponseBody
	public  Database addDatabase(@ModelAttribute DatabaseAddDto dto,
			HttpServletRequest request,
			HttpServletResponse response) {
		return databaseService.addDatabase(dto);

	}

	@RequestMapping("/modify")
	public void modifyDatabase() {

	}

	@RequestMapping("/delete")
	public void deleteDatabase() {

	}

	@RequestMapping("/list")
	public String index(){
		return "index";
	}

}