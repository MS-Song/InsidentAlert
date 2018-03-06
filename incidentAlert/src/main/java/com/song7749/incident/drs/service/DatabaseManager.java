package com.song7749.incident.drs.service;

import java.util.List;

import com.song7749.incident.drs.value.DatabaseAddDto;
import com.song7749.incident.drs.value.DatabaseModifyDto;
import com.song7749.incident.drs.value.DatabaseVo;

/**
 * <pre>
 * Class Name : DatabaseManager.java
 * Description : Database Manager
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 2. 25.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 2. 25.
*/
public interface DatabaseManager {

	DatabaseVo addDatabase(DatabaseAddDto dto);

	DatabaseVo modifyDatabase(DatabaseModifyDto dto);

	Integer AddOrModifyDatabaseFasade(List<DatabaseModifyDto> dtos);

	void deleteDatabase(Long id);
}
