package com.song7749.incident.web;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.song7749.incident.drs.type.Charset;
import com.song7749.incident.drs.type.DatabaseDriver;

public class DatabaseControllerTest extends ControllerTest{

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MockMvc mvc;

	private MockHttpServletRequestBuilder drb;

	MvcResult result;
	Map<String, Object> responseObject;

	@Test
	@SuppressWarnings("unchecked")
	public void testDatabaseAdd() throws Exception {

		// give
		drb=post("/database/add").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
				.param("name", "test")
				.param("schemaName", "test")
				.param("host", "test")
				.param("hostAlias", "test")
				.param("account", "song7749")
				.param("password", "1234")
				.param("port", "3306")
				.param("driver", DatabaseDriver.MYSQL.toString())
				.param("charset", Charset.UTF8.toString())
				;

		// when
		result = mvc.perform(drb)
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		 responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(),HashMap.class);

		 // then
		 assertThat(responseObject.get("httpStatus"), equalTo(200));
		 assertThat(responseObject.get("contents"), notNullValue());
		 assertThat(responseObject.get("rowCount"), notNullValue());

	}

}
