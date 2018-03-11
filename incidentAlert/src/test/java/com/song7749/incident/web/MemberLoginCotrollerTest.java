package com.song7749.incident.web;

import static com.song7749.util.LogMessageFormatter.format;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import com.song7749.incident.drs.service.MemberManager;
import com.song7749.incident.drs.value.MemberAddDto;

@SuppressWarnings("unchecked")
public class MemberLoginCotrollerTest extends ControllerTest{


	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MockMvc mvc;

	private MockHttpServletRequestBuilder drb;

	MvcResult result;
	Map<String, Object> responseObject;

	@Autowired
	MemberManager memberManager;

	// 테스트를 위한 회원 등록
	MemberAddDto dto = new MemberAddDto(
			"song12345678@gmail.com",
			"123456789",
			"passwordQuestion",
			"passwordAnswer",
			"teamName",
			"name");

	@Before
	public void setup(){
		// 테스트를 위한 회원 등록
		memberManager.addMemeber(dto);
	}

	@Test
	public void testDoLogin_fail_login_email() throws Exception {
		// give
		drb=post("/member/doLogin").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
				.param("loginId", "song7749")
				.param("password", dto.getPassword())
				;

		// when // then
		result = mvc.perform(drb)
				.andExpect(status().isBadRequest())
				.andDo(print())
				.andReturn();

		 responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(),HashMap.class);

		 // then
		 assertThat(responseObject.get("httpStatus"), equalTo(400));
		 assertThat(responseObject.get("contents"), nullValue());
		 assertThat(responseObject.get("rowCount"), nullValue());
		 assertThat(responseObject.get("message"), equalTo("loginId 의 Email 은(는) 이메일 주소가 유효하지 않습니다."));

	}

	@Test
	public void testDoLogin_fail_wrong_password() throws Exception {
		// give
		drb=post("/member/doLogin").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
				.param("loginId", dto.getLoginId())
				.param("password", "1234")
				;

		// when // then
		result = mvc.perform(drb)
				.andExpect(status().isBadRequest())
				.andDo(print())
				.andReturn();

		 responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(),HashMap.class);

		 // then
		 assertThat(responseObject.get("httpStatus"), equalTo(400));
		 assertThat(responseObject.get("contents"), nullValue());
		 assertThat(responseObject.get("rowCount"), nullValue());
		 assertThat(responseObject.get("message"), equalTo("password 의 Size 은(는) 반드시 최소값 8과(와) 최대값 20 사이의 크기이어야 합니다."));

	}

	@Test
	public void testDoLogin() throws Exception {
		// give
		drb=post("/member/doLogin").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
				.param("loginId", dto.getLoginId())
				.param("password", dto.getPassword())
				;

		// when
		result = mvc.perform(drb)
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		String cipher = result.getResponse().getCookie("cipher").getValue();
		responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(),HashMap.class);

		logger.trace(format("{}", "Login info"),responseObject);

		// then
		assertThat(responseObject.get("httpStatus"), equalTo(200));
		assertThat(responseObject.get("message"), notNullValue());
		assertThat(cipher,notNullValue());

	}

	@Test
	public void testDoLogout() throws Exception {
		// give - 로그인 실행
		drb=post("/member/doLogin").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
				.param("loginId", dto.getLoginId())
				.param("password", dto.getPassword())
				;

		result = mvc.perform(drb)
				.andExpect(status().isOk())
				.andReturn();

		// when
		drb=post("/member/doLogout").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA);

		// 로그인 cookie 정보 추가
		drb.cookie(new Cookie("cipher", result.getResponse().getCookie("cipher").getValue()));

		result = mvc.perform(drb)
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		// then
		assertThat(result.getResponse().getCookie("cipher").getValue(), equalTo(""));
	}

	@Test
	public void testGetLogin() throws Exception {
		// give - 로그인 실행
		drb=post("/member/doLogin").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA)
				.param("loginId", dto.getLoginId())
				.param("password", dto.getPassword())
				;

		result = mvc.perform(drb)
				.andExpect(status().isOk())
				.andReturn();

		// when
		drb=get("/member/getLogin").accept(MediaType.APPLICATION_JSON).locale(Locale.KOREA);

		// 로그인 cookie 정보 추가
		drb.cookie(new Cookie("cipher", result.getResponse().getCookie("cipher").getValue()));

		result = mvc.perform(drb)
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		// then
		responseObject = new ObjectMapper().readValue(result.getResponse().getContentAsString(),HashMap.class);

		logger.trace(format("{}", "Login Member Info"),responseObject);

		// then
		assertThat(responseObject.get("httpStatus"), equalTo(200));
		assertThat(responseObject.get("contents"), notNullValue());
	}
}