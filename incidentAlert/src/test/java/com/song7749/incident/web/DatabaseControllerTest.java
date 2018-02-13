package com.song7749.incident.web;

import static com.song7749.util.LogMessageFormatter.format;
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
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.song7749.incident.type.Charset;

public class DatabaseControllerTest extends ControllerTest{

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MockMvc mvc;

	private MockHttpServletRequestBuilder drb;

	MvcResult result;
	Map<String, Object> responseObject;

	@SuppressWarnings("unchecked")
	@Test
	public void testDatabaseAdd() throws Exception {
		drb=post("/database/add").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
				.param("name", "test")
				.param("schemaName", "test")
				.param("host", "test")
				.param("hostAlias", "test")
				.param("account", "song7749")
				.param("password", "1234")
				.param("port", "3306")
				.param("driver", DatabaseDriver.H2.toString())
				.param("charset", Charset.UTF8.toString())
				;


		result = mvc.perform(drb)
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		 responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(),HashMap.class);

		 logger.trace(format("{}", "Log Message"),responseObject);
	}

}
