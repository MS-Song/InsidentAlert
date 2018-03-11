package com.song7749.incident.drs.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import com.song7749.base.Compare;
import com.song7749.incident.drs.domain.Database;
import com.song7749.incident.drs.domain.Member;
import com.song7749.incident.drs.repository.DatabaseRepository;
import com.song7749.incident.drs.repository.MemberRepository;
import com.song7749.incident.drs.type.AuthType;
import com.song7749.incident.drs.type.Charset;
import com.song7749.incident.drs.type.DatabaseDriver;
import com.song7749.incident.drs.type.MemberModifyByAdminDto;
import com.song7749.incident.drs.value.MemberAddDto;
import com.song7749.incident.drs.value.MemberFindDto;
import com.song7749.incident.drs.value.MemberModfyDatabaseDto;
import com.song7749.incident.drs.value.MemberModifyDto;
import com.song7749.incident.drs.value.MemberVo;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({"com.song7749.incident.drs"})
public class MemberManagerImplTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	MemberManager memberManager;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	DatabaseRepository databaseRepository;

	@Autowired
	ModelMapper mapper;

	/**
	 * fixture
	 */
	Member member = new Member(
			"song7749@gmail.com"
			, "12345678"
			, "패스워드질문은?"
			, "패스워드답변은?"
			, "제일잘나가는팀"
			, "song7749"
			, AuthType.ADMIN);


	MemberAddDto memberAddDto = new MemberAddDto(
			"song7749@test.com"
			, "12345678"
			, "패스워드질문은?"
			, "패스워드답변은?"
			, "제일잘나가는팀"
			, "song7749");

	Database ds = new Database("10.10.10.10"
			, "test server"
			, "dbTest"
			, "song7749"
			, "12345678"
			, DatabaseDriver.ORACLE
			, Charset.UTF8
			, "3333"
			, "");

	@Test
	public void testModelMapperMemberToMemberVo() {
		mapper.map(member, MemberVo.class);
	}

	@Test
	public void testModelMapperMemberAddDtoToMember() {
		mapper.map(memberAddDto, member);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testAddMemeberGivenLoginIdIsNull() throws Exception {
		// give
		memberAddDto.setLoginId(null);
		// when
		memberManager.addMemeber(memberAddDto);
		// then expected
	}
	@Test
	public void testAddMemeber() throws Exception {
		// give
		// when
		MemberVo vo = memberManager.addMemeber(memberAddDto);
		// then
		assertThat(vo.getId(),notNullValue());
	}

	@Test
	public void testModifyMember() throws Exception {
		// give
		MemberVo vo = memberManager.addMemeber(memberAddDto);
		MemberModifyDto dto = mapper.map(vo, MemberModifyDto.class);
		dto.setPassword("abcdefghij123gdg");
		dto.setName("song1234");
		dto.setTeamName("abcd team");

		// when
		vo = memberManager.modifyMember(dto);
		// then
		assertThat(dto.getName(), equalTo(vo.getName()));
		assertThat(dto.getTeamName(), equalTo(vo.getTeamName()));
	}

	@Test
	public void testModifyMemberMemberModifyByAdminDto() throws Exception {
		//give
		MemberVo vo = memberManager.addMemeber(memberAddDto);
		MemberModifyByAdminDto dto = mapper.map(vo, MemberModifyByAdminDto.class);
		dto.setPassword("abcdefghij123gdg");
		dto.setName("song1234");
		dto.setTeamName("abcd team");
		dto.setAuthType(AuthType.ADMIN);

		// when
		vo = memberManager.modifyMember(dto);

		// then
		assertThat(dto.getName(), equalTo(vo.getName()));
		assertThat(dto.getTeamName(), equalTo(vo.getTeamName()));
		assertThat(dto.getAuthType(), equalTo(vo.getAuthType()));

	}

	@Test
	public void testModifyMemberMemberModfyDatabaseDto() throws Exception {
		//give
		Database d = databaseRepository.saveAndFlush(ds);
		MemberVo vo = memberManager.addMemeber(memberAddDto);
		MemberModfyDatabaseDto dto = new MemberModfyDatabaseDto(d.getId(), vo.getId());

		// when
		memberManager.modifyMember(dto);
		Member m = memberRepository.findById(vo.getId()).get();

		// then
		assertThat(m.getMemberDatabaseList().size(), equalTo(1));

		// give
		dto = new MemberModfyDatabaseDto(
				m.getMemberDatabaseList().get(0).getId()
				, d.getId(), vo.getId());
		// when
		memberManager.modifyMember(dto);
		m = memberRepository.findById(vo.getId()).get();

		// then
		assertThat(m.getMemberDatabaseList().size(), equalTo(0));

	}

	@Test
	public void testFindMemberList() throws Exception {
		// give
		memberManager.addMemeber(memberAddDto);
		memberAddDto.setLoginId("song1@test.com");
		memberManager.addMemeber(memberAddDto);
		memberAddDto.setLoginId("song2@test.com");
		memberManager.addMemeber(memberAddDto);
		memberAddDto.setLoginId("song3@test.com");
		memberManager.addMemeber(memberAddDto);
		memberAddDto.setLoginId("song4@test.com");
		memberManager.addMemeber(memberAddDto);
		memberAddDto.setLoginId("song5@test.com");
		memberManager.addMemeber(memberAddDto);
		memberAddDto.setLoginId("song6@test.com");
		memberManager.addMemeber(memberAddDto);
		memberAddDto.setLoginId("song7@test.com");
		memberManager.addMemeber(memberAddDto);
		memberAddDto.setLoginId("song8@test.com");
		memberManager.addMemeber(memberAddDto);

		MemberFindDto dto = new MemberFindDto();
		// when
		List<MemberVo> voList = memberManager.findMemberList(dto);
		//then
		assertTrue(voList.size() >= 8);

		// give
		// when
		for(MemberVo vo : voList) {
			dto = new MemberFindDto();
			List<MemberVo> list = null;

			dto.setId(vo.getId());
			list = memberManager.findMemberList(dto);
			// then
			assertTrue(list.size() == 1);

			dto.setLoginId(vo.getLoginId());
			list = memberManager.findMemberList(dto);
			// then
			assertTrue(list.size() == 1);

			dto.setLoginIdCompare(Compare.LIKE);
			list = memberManager.findMemberList(dto);
			// then
			assertTrue(list.size() == 1);

			dto.setName(vo.getName());
			list = memberManager.findMemberList(dto);
			// then
			assertTrue(list.size() == 1);

			dto.setNameCompare(Compare.LIKE);
			list = memberManager.findMemberList(dto);
			// then
			assertTrue(list.size() == 1);

			dto.setTeamName(vo.getTeamName());
			list = memberManager.findMemberList(dto);
			// then
			assertTrue(list.size() == 1);

			dto.setTeamNameCompare(Compare.LIKE);
			list = memberManager.findMemberList(dto);
			// then
			assertTrue(list.size() == 1);
		}
	}

	@Test
	public void testRemoveMember() throws Exception {
		// give
		MemberVo vo = memberManager.addMemeber(memberAddDto);
		MemberFindDto dto = new MemberFindDto();
		// when
		memberManager.removeMember(vo.getId());
		dto.setId(vo.getId());
		List<MemberVo> list = memberManager.findMemberList(dto);
		// then
		assertTrue(list.size() == 0);
	}

	@Test
	public void testFindMemberLong() throws Exception {
		// give
		MemberVo vo = memberManager.addMemeber(memberAddDto);
		// when
		MemberVo vo2 = memberManager.findMember(vo.getId());
		// then
		assertThat(vo.getId(), equalTo(vo2.getId()));
	}

	@Test
	public void testFindMemberLongReturnNull() throws Exception {
		// give
		Long id = 20L;
		// when
		MemberVo vo2 = memberManager.findMember(id);
		// then
		assertNull(vo2);
	}

	@Test
	public void testFindMemberString() throws Exception {
		// give
		MemberVo vo = memberManager.addMemeber(memberAddDto);
		// when
		MemberVo vo2 = memberManager.findMember(vo.getLoginId());
		// then
		assertThat(vo.getLoginId(), equalTo(vo2.getLoginId()));
	}

	@Test
	public void testFindMemberStringReturnNull() throws Exception {
		// give
		String loginId="song9999@gmail.com";
		// when
		MemberVo vo2 = memberManager.findMember(loginId);
		// then
		assertNull(vo2);
	}

	@Test
	public void testFindMemberStringString() throws Exception {
		// give
		MemberVo vo = memberManager.addMemeber(memberAddDto);
		// when
		MemberVo vo2 = memberManager.findMember(vo.getLoginId(), "12345678");
		// then
		assertThat(vo.getLoginId(), equalTo(vo2.getLoginId()));

	}
}