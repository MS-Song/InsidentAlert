package com.song7749.incident.drs.session;

import static com.song7749.util.LogMessageFormatter.format;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import com.song7749.incident.drs.value.LoginAuthVo;

@Component("loginSession")
@RequestScope
public class LoginSession {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	ModelMapper mapper;

	private LoginAuthVo lav;

	public LoginSession() {}

	public void setSesseion(LoginAuthVo lav) throws IllegalArgumentException {
		if(!isLogin()) {
			logger.trace(format("{}", "Session 생성"),lav);
			this.lav=lav;
		}
		else throw new IllegalArgumentException("이미 로그인 상태로 세션 값을 변경할 수 없습니다.");
	}

	public boolean isLogin() {
		return null!=lav;
	}

	public LoginAuthVo getLogin() {
		return isLogin() ? lav : null;
	}
}