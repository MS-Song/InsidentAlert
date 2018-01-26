package com.song7749.incident.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.song7749.incident.domain.Database;
import com.song7749.incident.type.Charset;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan("com.song7749")
public class DatabaseRepositoryTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	DatabaseRepository databaseRepository;

	/**
	 * fixture
	 */
	Database ds = new Database("10.10.10.10"
			, "test server"
			, "dbTest"
			, "song7749"
			, "12345678"
			, DatabaseDriver.H2
			, Charset.UTF8
			, "3333",
			new Date());

	@Test(expected=InvalidDataAccessApiUsageException.class)
	public void testSaveGivenNull() {
		//give
		//when
		databaseRepository.saveAndFlush(null);
		//then -- expacted
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSaveGivenNullHost() {
		//give
		ds.setHost(null);
		//when
		databaseRepository.saveAndFlush(ds);
		//then -- expacted

	}

	@Test
	public void testSave() {
		//give
		//when
		ds = databaseRepository.saveAndFlush(ds);
		//than
		assertThat(ds.getId(),notNullValue());

		//give
		ds.setHost("10.200.200.200");
		ds.setCharset(Charset.EUCKR);
		//when
		Database dsi = databaseRepository.saveAndFlush(ds);
		//then
		assertThat(dsi.getId(),equalTo(ds.getId()));
		assertThat(dsi.getHost(),equalTo(ds.getHost()));
		assertThat(dsi.getCharset(),equalTo(ds.getCharset()));
	}
}