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

import javax.servlet.http.Cookie;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.song7749.incident.drs.service.DatabaseManager;
import com.song7749.incident.drs.service.MemberManager;
import com.song7749.incident.drs.type.Charset;
import com.song7749.incident.drs.type.DatabaseDriver;
import com.song7749.incident.drs.value.DatabaseAddDto;
import com.song7749.incident.drs.value.MemberAddDto;

@SuppressWarnings("unchecked")
public class DatabaseControllerTest extends ControllerTest{

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MockMvc mvc;

	private MockHttpServletRequestBuilder drb;

	MvcResult result;
	Map<String, Object> responseObject;

	@Autowired
	DatabaseManager databaseManager;

	@Autowired
	MemberManager memberManager;

	// 테스트를 위한 회원 등록
	MemberAddDto mad = new MemberAddDto(
			"song12345678@gmail.com",
			"123456789",
			"passwordQuestion",
			"passwordAnswer",
			"teamName",
			"name");

	// 테스트를 위한 DB 등록
	DatabaseAddDto dto = new DatabaseAddDto(
			"testHost",
			"testHostAlias",
			"testSchemaName",
			"testAccount",
			"testPassword",
			DatabaseDriver.MYSQL,
			Charset.UTF8,
			"3306");

	@Before
	public void setup(){
		// 테스트를 위한 회원 등록
		memberManager.addMemeber(mad);
		// 테스트를 위한 databae 등록
		databaseManager.addDatabase(dto);
	}

	@Test
	public void testDatabaseAdd() throws Exception {

		// give
		// -- 로그인 처리
		drb=post("/member/doLogin").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
				.param("loginId", mad.getLoginId())
				.param("password", mad.getPassword())
				;
		//-- 로그인 request
		result = mvc.perform(drb).andExpect(status().isOk()).andDo(print()).andReturn();

		drb=post("/database/add").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
				.param("host", dto.getHost())
				.param("hostAlias", dto.getHostAlias())
				.param("schemaName", dto.getSchemaName())
				.param("account", dto.getAccount())
				.param("password", dto.getPassword())
				.param("port", dto.getPort())
				.param("driver", dto.getDriver().toString())
				.param("charset", dto.getCharset().toString())
				;

		// 로그인 cookie 정보 추가
		drb.cookie(new Cookie("cipher", result.getResponse().getCookie("cipher").getValue()));

		// when
		result = mvc.perform(drb)
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		 responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(),HashMap.class);

		 // then
		 assertThat(responseObject.get("httpStatus"), equalTo(200));
		 assertThat(responseObject.get("contents"), notNullValue());
		 assertThat(responseObject.get("rowCount"), equalTo(1));
		 assertThat(responseObject.get("message"), equalTo("Database 정보가 저장 되었습니다."));

	}

	@Test
	public void testDatabaseAdd_No_Login() throws Exception {

		// give
		drb=post("/database/add").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
				.param("host", dto.getHost())
				.param("hostAlias", dto.getHostAlias())
				.param("schemaName", dto.getSchemaName())
				.param("account", dto.getAccount())
				.param("password", dto.getPassword())
				.param("port", dto.getPort())
				.param("driver", dto.getDriver().toString())
				.param("charset", dto.getCharset().toString())
				;

		// when
		result = mvc.perform(drb)
				.andExpect(status().isMethodNotAllowed())
				.andDo(print())
				.andReturn();

		 responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(),HashMap.class);

		 // then
		 assertThat(responseObject.get("httpStatus"), equalTo(405));
		 assertThat(responseObject.get("message"), equalTo("로그인이 필요한 서비스입니다. 로그인 해주시기 바랍니다."));
	}
}