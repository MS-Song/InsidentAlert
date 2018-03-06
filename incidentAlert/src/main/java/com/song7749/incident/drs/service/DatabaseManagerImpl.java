package com.song7749.incident.drs.service;


import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.song7749.incident.drs.domain.Database;
import com.song7749.incident.drs.repository.DatabaseRepository;
import com.song7749.incident.drs.value.DatabaseAddDto;
import com.song7749.incident.drs.value.DatabaseModifyDto;
import com.song7749.incident.drs.value.DatabaseVo;
import com.song7749.util.validate.Validate;

/**
 * <pre>
 * Class Name : databaseManager.java
 * Description : Database 관련 처리를 담당하는 Manager 구현체
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

@Service("databaseManager")
public class DatabaseManagerImpl implements DatabaseManager{

	@Autowired
	DatabaseRepository databaseRepository;

	@Autowired
	ModelMapper mapper;

	@Override
	@Validate
	@Transactional
	public DatabaseVo addDatabase(DatabaseAddDto dto) {
		return mapper.map(databaseRepository.saveAndFlush(mapper.map(dto, Database.class)),DatabaseVo.class);
	}

	@Override
	@Validate
	@Transactional
	public DatabaseVo modifyDatabase(DatabaseModifyDto dto) {
		return mapper.map(databaseRepository.saveAndFlush(mapper.map(dto, Database.class)),DatabaseVo.class);
	}

	@Override
	@Validate
	@Transactional
	public Integer AddOrModifyDatabaseFasade(List<DatabaseModifyDto> dtos) {
		List<Database> databases = new ArrayList<Database>();
		for(DatabaseModifyDto dto : dtos) {
			databases.add(mapper.map(dto, Database.class));
		}

		databaseRepository.saveAll(databases);
		databaseRepository.flush();

		return databases.size();
	}

	@Override
	@Validate
	@Transactional
	public void deleteDatabase(Long id) {
		databaseRepository.deleteById(id);
		databaseRepository.flush();
	}
}