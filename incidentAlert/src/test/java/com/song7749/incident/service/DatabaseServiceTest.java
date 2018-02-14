package com.song7749.incident.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import com.song7749.incident.domain.Database;
import com.song7749.incident.type.Charset;
import com.song7749.incident.value.DatabaseAddDto;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({"com.song7749.incident.aop"
	,"com.song7749.incident.config"
	,"com.song7749.incident.service"})
public class DatabaseServiceTest {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	ModelMapper mapper;
	/**
	 * Fixture
	 */
	DatabaseAddDto databaseAddDto = new DatabaseAddDto("10.10.10.10", "Test Database", "Test Schema", "song7749",
			"1234", DatabaseDriver.H2, Charset.UTF8, "3306", new Date());


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
		databaseService.addDatabase(dto);
		//then -- expected
	}

	@Test(expected=IllegalArgumentException.class)
	public void testAddDatabaseGivenHostNull() {
		//give
		databaseAddDto.setHost(null);
		//when
		databaseService.addDatabase(databaseAddDto);
		//then expected
	}

	@Test
	public void testAddDatabase() {
		//give
		//when
		Database ds = databaseService.addDatabase(databaseAddDto);
		//then
		assertThat(ds.getId(), notNullValue());
	}
}