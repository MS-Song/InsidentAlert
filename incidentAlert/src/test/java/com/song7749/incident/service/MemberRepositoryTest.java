package com.song7749.incident.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import com.song7749.incident.domain.Member;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan("com.song7749")
public class MemberRepositoryTest {

	@Autowired
	MemberRepository memberRepository;

	/**
	 * fixture
	 */
	Member member = new Member("song7749@gmail.com", "1234",
			"any team", "minsoo", new Date(), new Date());

	@Test
	public void tesetSave() {
		//give
		//when
		Member m1 = memberRepository.save(member);
		//then
		assertThat(m1.getId(),notNullValue());

		//give
		m1.setName("Song");
		//when
		Member m2 = memberRepository.save(m1);
		//then
		assertThat(m1.getName(),equalTo(m2.getName()));

		//give
		//when
		Optional<Member> m3 = memberRepository.findById(m1.getId());
		//then
		assertThat(m2.getId(), equalTo(m3.get().getId()));
	}

}