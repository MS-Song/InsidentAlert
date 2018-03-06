package com.song7749.incident.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.song7749.incident.drs.service.LoginManager;
import com.song7749.incident.drs.service.MemberManager;
import com.song7749.incident.drs.value.LoginDoDTO;
import com.song7749.incident.drs.value.MemberVo;
import com.song7749.util.StringUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;



@Api(tags = "회원 로그인 기능")
@RestController
@RequestMapping("/member")
public class MemberLoginCotroller {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	LoginManager loginManager;

	@Autowired
	MemberManager memberManager;

	@ApiOperation(value = "회원 로그인",notes = "회원 ID/PASSWORD 를 받아서 로그인 cookie 를 생성 한다.")
	@PostMapping(value="/doLogin")
	@ResponseBody
	public ModelMap doLogin(
			@Valid @ModelAttribute LoginDoDTO dto,
			HttpServletRequest request,
			HttpServletResponse response,
			ModelMap model){

		loginManager.doLogin(dto, request, response);

		model.clear();
		model.addAttribute("message","로그인 처리가 완료되었습니다.");
		return model;
	}

	@ApiOperation(value = "회원 로그아웃",notes = "로그 아웃 프로세스를 실행한다.")
	@PostMapping(value="/doLogout")
	@ResponseBody
	public ModelMap doLogout(
			HttpServletRequest request,
			HttpServletResponse response,
			ModelMap model){

		loginManager.doLogout(response);

		model.clear();
		model.addAttribute("message","로그아웃 처리가 완료되었습니다.");
		return model;
	}


	@ApiOperation(value = "로그인 정보 획득",notes = "cookie 에 저장되어 있는 로그인 정보를 획득한다.")
	@GetMapping(value="/getLogin")
	@ResponseBody
	public MemberVo getLogin(
			HttpServletRequest request,
			ModelMap model){

		String loginId = loginManager.getLoginID(request);

		MemberVo vo = null;
		if(!StringUtils.isBlank(loginId)){
			vo=memberManager.findMember(loginId);
		} else {
			vo = new MemberVo();
		}
		return vo;
	}
}