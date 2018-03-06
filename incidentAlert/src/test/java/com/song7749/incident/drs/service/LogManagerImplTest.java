package com.song7749.incident.drs.service;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import com.song7749.incident.drs.domain.Log;
import com.song7749.incident.drs.repository.LogRepository;
import com.song7749.incident.drs.value.LogLoginAddDto;
import com.song7749.incident.drs.value.LogQueryAddDto;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({"com.song7749.incident.drs"})
public class LogManagerImplTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	LogManager logManager;

	@Autowired
	LogRepository logRepository;

	@Autowired
	ModelMapper mapper;

	@Test
	public void testAddLogLogin() throws Exception {
		// give
		LogLoginAddDto dto = new LogLoginAddDto("10.10.10.10"
				, "song7749@gmail.com"
				, "asdfasfasdfasdf");

		// when
		logManager.addLogLogin(dto);
		// then
		Thread.sleep(1000);
		List<Log> logList = logRepository.findAll();

		assertTrue(logList.size()>0);

	}

	@Test
	public void testAddQueryExecuteLog() throws Exception {
		// give
		LogQueryAddDto dto = new LogQueryAddDto("10.10.10.10"
				, "song7749@gmail.com"
				, 1L
				, "host"
				, "hostAlias"
				, "schemaName"
				, "account"
				, "query");

		// when
		logManager.addQueryExecuteLog(dto);
		// then
		Thread.sleep(1000);
		List<Log> logList = logRepository.findAll();
		assertTrue(logList.size()>0);
	}
}