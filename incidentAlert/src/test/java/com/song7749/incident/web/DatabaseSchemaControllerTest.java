package com.song7749.incident.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.song7749.incident.drs.service.DatabaseManager;
import com.song7749.incident.drs.service.MemberManager;
import com.song7749.incident.drs.type.Charset;
import com.song7749.incident.drs.type.DatabaseDriver;
import com.song7749.incident.drs.value.DatabaseAddDto;
import com.song7749.incident.drs.value.DatabaseVo;
import com.song7749.incident.drs.value.MemberAddDto;
import com.song7749.incident.drs.value.MemberVo;

public class DatabaseSchemaControllerTest extends ControllerTest{
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
	MemberAddDto dto = new MemberAddDto(
			"song1234@gmail.com"
			, "123456789"
			, "passwordQuestion"
			, "passwordAnswer"
			, "teamName"
			, "name");

	// 테스트를 위한 DB 등록
	DatabaseAddDto dad = new DatabaseAddDto(
			"testHost",
			"testHostAlias",
			"testSchemaName",
			"testAccount",
			"testPassword",
			DatabaseDriver.MYSQL,
			Charset.UTF8,
			"3306");

	MemberVo vo;
	DatabaseVo dv;
	Cookie cookie;

	@Before
	public void setup() throws Exception{
		// 테스트를 위한 회원 등록
		vo = memberManager.addMemeber(dto);
		// 테스트를 위한 databae 등록
		dv = databaseManager.addDatabase(dad);

		// give - 로그인 실행
		drb=post("/member/doLogin").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
				.param("loginId", dto.getLoginId())
				.param("password", dto.getPassword())
				;
		// when
		result = mvc.perform(drb)
				.andExpect(status().isOk())
				.andReturn();
		// login cookie 생성
		cookie = new Cookie("cipher", result.getResponse().getCookie("cipher").getValue());
	}


}
