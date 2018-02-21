package com.song7749.incident.service;


import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.song7749.incident.domain.Database;
import com.song7749.incident.validate.Validate;
import com.song7749.incident.value.DatabaseAddDto;
import com.song7749.incident.value.DatabaseModifyDto;
import com.song7749.incident.value.DatabaseVo;

/**
 * <pre>
 * Class Name : databaseManager.java
 * Description : Database 관련 처리를 담당하는 Manager
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
public class DatabaseManager{

	@Autowired
	DatabaseRepository databaseRepository;

	@Autowired
	ModelMapper mapper;

	@Validate
	@Transactional
	public DatabaseVo addDatabase(DatabaseAddDto dto) {
		return mapper.map(databaseRepository.saveAndFlush(mapper.map(dto, Database.class)),DatabaseVo.class);
	}

	@Validate
	@Transactional
	public DatabaseVo modifyDatabase(DatabaseModifyDto dto) {
		return mapper.map(databaseRepository.saveAndFlush(mapper.map(dto, Database.class)),DatabaseVo.class);
	}

	/**
	 * Database 객체를 모두 저장하는 Method
	 * ID 가 null 인 경우에는 Add, ID 가 exist 인 경우에는 Modify 한다.
	 * TODO Validate 추가 필요
	 * @param dtos
	 * @return Integer Affected Rows (실제로는 Affected Row 식별이 불가능 함)
	 */
	@Validate
	@Transactional
	public Integer AddOrModifyDatabaseAll(List<DatabaseModifyDto> dtos) {
		List<Database> databases = new ArrayList<Database>();
		for(DatabaseModifyDto dto : dtos) {
			databases.add(mapper.map(dto, Database.class));
		}

		databaseRepository.saveAll(databases);
		databaseRepository.flush();

		return databases.size();
	}

	@Validate
	@Transactional
	public void deleteDatabase(Long id) {
		databaseRepository.deleteById(id);
		databaseRepository.flush();
	}
}