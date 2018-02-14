package com.song7749.incident.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.song7749.incident.domain.Database;
import com.song7749.incident.validate.Validate;
import com.song7749.incident.value.DatabaseAddDto;
import com.song7749.incident.value.DatabaseModifyDto;

/**
 * <pre>
 * Class Name : databaseService.java
 * Description : Database 관련 처리를 담당하는 서비스
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 1. 23.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 1. 23.
*/

@Service
public class DatabaseService{

	@Autowired
	DatabaseRepository databaseRepository;

	@Autowired
	ModelMapper mapper;

	@Validate
	@Transactional
	public Database addDatabase(DatabaseAddDto dto) {
		return databaseRepository.saveAndFlush(mapper.map(dto, Database.class));
	}

	@Validate
	@Transactional
	public Database modifyDatabase(DatabaseModifyDto dto) {
		return databaseRepository.saveAndFlush(mapper.map(dto, Database.class));
	}
}