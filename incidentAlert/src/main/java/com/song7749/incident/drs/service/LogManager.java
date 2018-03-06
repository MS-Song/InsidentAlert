package com.song7749.incident.drs.service;

import java.util.List;

import com.song7749.incident.drs.value.LogLoginAddDto;
import com.song7749.incident.drs.value.LogLoginFindDto;
import com.song7749.incident.drs.value.LogLoginVo;
import com.song7749.incident.drs.value.LogQueryAddDto;
import com.song7749.incident.drs.value.LogQueryFindDto;
import com.song7749.incident.drs.value.LogQueryVo;

/**
 * <pre>
 * Class Name : LogManager.java
 * Description : 로그를 기록하고 조회하는 매니저
*
*  Modification Information
*  Modify Date 		Modifier	Comment
*  -----------------------------------------------
*  2016. 2. 23.		song7749	신규작성
*
* </pre>
*
* @author song7749
* @since 2016. 2. 23.
*/
public interface LogManager {

	/**
	 * 회원 로그인 로그를 기록한다.
	 * @param dto
	 */
	public void addLogLogin(LogLoginAddDto dto);

	/**
	 * 회원 로그인 로그를 조회한다.
	 * @param dto
	 * @return List<LogLoginVo>
	 */
	public List<LogLoginVo> findMemberLoginLogList(LogLoginFindDto dto);


	/**
	 * 실행한 쿼리 로그를 기록한다.
	 * @param dto
	 */
	public void addQueryExecuteLog(LogQueryAddDto dto);

	/**
	 * 실행한 쿼리 로그를 조회한다.
	 * @param dto
	 * @return List<LogQueryVo>
	 */
	public List<LogQueryVo> findQueryExecuteLog(LogQueryFindDto dto);
}
