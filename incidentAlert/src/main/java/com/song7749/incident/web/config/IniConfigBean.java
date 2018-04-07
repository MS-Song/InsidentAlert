package com.song7749.incident.web.config;

import static com.song7749.util.LogMessageFormatter.format;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.song7749.incident.drs.domain.Database;
import com.song7749.incident.drs.domain.Member;
import com.song7749.incident.drs.repository.DatabaseRepository;
import com.song7749.incident.drs.repository.MemberRepository;
import com.song7749.incident.drs.service.MemberManager;
import com.song7749.incident.drs.type.AuthType;
import com.song7749.incident.drs.type.Charset;
import com.song7749.incident.drs.type.DatabaseDriver;
import com.song7749.incident.drs.value.MemberVo;

/**
 * <pre>
 * Class Name : IniConfigBean.java
 * Description : App 실행 시 사전에 처리해야 하는 작업들을 정의 함.
 *
 * 1. Root 유저 등록
 * 2. Local - H2 DB 에 대한 내용 확인
 * 3. 테스트 Data 입력 -- 서비스 배포시에는 삭제 필요
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 3. 29.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 3. 29.
*/

@Component
public class IniConfigBean {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	DatabaseRepository databaseRepository;

	@Autowired
	MemberManager memberManager;

	@Transactional
	@PostConstruct
    public void init(){


		// root 회원에 대한 입력
		Member member = new Member(
				"root@test.com"
				, "12345678"
				, "Password Question?"
				, "Password Answer?"
				, "team name"
				, "your name"
				, AuthType.ADMIN);
		Member aleadyMember = memberRepository.findByLoginId("root@test.com");
		if(null==aleadyMember) {
			memberRepository.saveAndFlush(member);
			MemberVo memberVo = memberManager.renewApikeyByAdmin(member.getLoginId());
			logger.trace(format("{}", "first Start Application with root user create"),memberVo);
		} else {
			logger.trace(format("{}", "root user info"),aleadyMember);
		}


		// root 유저입력

		Database mysql = new Database(
				"local-dev"
				, "mysql-local"
				, "dbBilling"
				, "song7749"
				, "94426dscd"
				, DatabaseDriver.MYSQL
				, Charset.UTF8
				, "3306"
				, null);

		databaseRepository.saveAndFlush(mysql);

		Database oracle = new Database(
				"local-dev"
				, "oracle-local"
				, "XE"
				, "SONG7749"
				, "94426dscd"
				, DatabaseDriver.ORACLE
				, Charset.UTF8
				, "1521"
				, null);
		databaseRepository.saveAndFlush(oracle);
    }
}
