package com.song7749.incident.drs.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import com.song7749.incident.drs.domain.Database;
import com.song7749.incident.drs.repository.DatabaseRepository;
import com.song7749.incident.drs.type.Charset;
import com.song7749.incident.drs.type.DatabaseDriver;
import com.song7749.incident.drs.value.DatabaseAddDto;
import com.song7749.incident.drs.value.DatabaseModifyDto;
import com.song7749.incident.drs.value.DatabaseVo;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({"com.song7749.incident.drs"})
public class DatabaseManagerImplTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	DatabaseManager databaseManager;

	@Autowired
	DatabaseRepository databaseRepository;

	@Autowired
	ModelMapper mapper;
	/**
	 * Fixture
	 */
	DatabaseAddDto databaseAddDto = new DatabaseAddDto("10.10.10.10", "Test Database", "Test Schema", "song7749",
			"1234", DatabaseDriver.MYSQL, Charset.UTF8, "3306");


	@Test
	public void testMapper() {

		//give
		//when
		Database database = mapper.map(databaseAddDto, Database.class);
		//then
		assertThat(database.getHost(),equalTo(database.getHost()));
		assertThat(database.getAccount(),equalTo(database.getAccount()));
		assertThat(database.getCharset(),equalTo(database.getCharset()));
		assertThat(database.getPort(),equalTo(database.getPort()));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testAddDatabaseGivenNull() {
		//give
		DatabaseAddDto dto = null;
		//when
		databaseManager.addDatabase(dto);
		//then -- expected
	}

	@Test(expected=IllegalArgumentException.class)
	public void testAddDatabaseGivenHostNull() {
		//give
		databaseAddDto.setHost(null);
		//when
		databaseManager.addDatabase(databaseAddDto);
		//then expected
	}

	@Test
	public void testAddDatabase() {
		//give
		//when
		DatabaseVo dv = databaseManager.addDatabase(databaseAddDto);
		//then
		assertThat(dv.getId(), notNullValue());
		assertThat(dv.getCreateDate(), notNullValue());
		assertThat(dv.getModifyDate(), notNullValue());
		assertEquals(dv.getCreateDate(), dv.getModifyDate());
	}

	@Test
	public void testModifyDatabase() throws Exception {
		//give
		DatabaseVo dv = databaseManager.addDatabase(databaseAddDto);


		//when
		Thread.sleep(1000);
		DatabaseVo rDv = databaseManager.modifyDatabase(mapper.map(dv, DatabaseModifyDto.class));
		//then
		assertNotEquals(dv.getModifyDate(), rDv.getModifyDate());
	}
}