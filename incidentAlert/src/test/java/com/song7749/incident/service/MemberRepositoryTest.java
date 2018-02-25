package com.song7749.incident.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import com.song7749.incident.domain.Database;
import com.song7749.incident.domain.Member;
import com.song7749.incident.domain.MemberDatabase;
import com.song7749.incident.domain.MemberSaveQuery;
import com.song7749.incident.type.AuthType;
import com.song7749.incident.type.Charset;
import com.song7749.incident.type.DatabaseDriver;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({"com.song7749.incident.aop"
	,"com.song7749.incident.config"
	,"com.song7749.incident.service"})
public class MemberRepositoryTest {

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	DatabaseRepository databaseRepository;

	/**
	 * fixture
	 */
	Member member = new Member("song7749@gmail.com"
			, "12345678"
			, "패스워드질문은?"
			, "패스워드답변은?"
			, "제일잘나가는팀"
			, "song7749"
			, AuthType.ADMIN);


	@Test
	public void tesetSave() {
		//give
		//when
		Member m1 = memberRepository.saveAndFlush(member);
		//then
		assertThat(m1.getId(),notNullValue());

		//give
		m1.setName("Song");
		//when
		Member m2 = memberRepository.saveAndFlush(m1);
		//then
		assertThat(m1.getName(),equalTo(m2.getName()));

		//give
		//when
		Optional<Member> m3 = memberRepository.findById(m1.getId());
		//then
		assertThat(m2.getId(), equalTo(m3.get().getId()));
	}

	@Test
	public void testSaveMemeberDatabase() {
		//give
		Database ds = new Database("10.10.10.10"
				, "test server"
				, "dbTest"
				, "song7749"
				, "12345678"
				, DatabaseDriver.ORACLE
				, Charset.UTF8
				, "3333"
				, "");

		databaseRepository.saveAndFlush(ds);

		MemberDatabase md = new MemberDatabase(ds);
		member.addMemberDatabaseList(md);
		//when
		Member m2 = memberRepository.saveAndFlush(member);
		//then
		Optional<Member> m3 = memberRepository.findById(m2.getId());

		assertEquals(m2.getId(), m3.get().getId());
		assertThat(m2.getMemberDatabaseList(), notNullValue());

		// remove child
		m2.getMemberDatabaseList().remove(0);
		memberRepository.saveAndFlush(m2);

	}


	@Test
	public void testSaveMemeberSaveQuery() {
		//give
		Database ds = new Database("10.10.10.10"
				, "test server"
				, "dbTest"
				, "song7749"
				, "12345678"
				, DatabaseDriver.MYSQL
				, Charset.UTF8
				, "3333"
				, "");

		databaseRepository.saveAndFlush(ds);

		MemberSaveQuery msd = new MemberSaveQuery("테스트메모 입니다."
				, "selelct * from dual", member, ds);

		member.addMemberSaveQueryList(msd);

		//when
		Member m2 = memberRepository.saveAndFlush(member);
		//then
		Optional<Member> m3 = memberRepository.findById(m2.getId());

		assertEquals(m2.getId(), m3.get().getId());
		assertThat(m2.getMemberSaveQueryList(), notNullValue());

		// remove child
		m2.getMemberSaveQueryList().remove(0);
		memberRepository.saveAndFlush(m2);

	}

}