package com.song7749.incident.drs.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.net.InetAddress;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import com.song7749.base.MessageVo;
import com.song7749.incident.drs.domain.Database;
import com.song7749.incident.drs.domain.Member;
import com.song7749.incident.drs.repository.DatabaseRepository;
import com.song7749.incident.drs.repository.MemberRepository;
import com.song7749.incident.drs.type.AuthType;
import com.song7749.incident.drs.type.Charset;
import com.song7749.incident.drs.type.DatabaseDriver;
import com.song7749.incident.drs.value.dbclient.ExecuteQueryDto;
import com.song7749.incident.drs.value.dbclient.FieldVo;
import com.song7749.incident.drs.value.dbclient.IndexVo;
import com.song7749.incident.drs.value.dbclient.TableVo;
import com.song7749.incident.drs.value.dbclient.ViewVo;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({"com.song7749.incident.drs"})
public class DBclienManagerImplTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	DBclienManager dbclienManager;

	@Autowired
	DatabaseRepository databaseRepository;

	@Autowired
	MemberRepository memberRepository;

	/**
	 * Fixture
	 */
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

	Member member = new Member("test@gmail.com"
			, "12345678"
			, "패스워드질문은?"
			, "패스워드답변은?"
			, "제일잘나가는팀"
			, "song7749"
			, AuthType.ADMIN);

	ExecuteQueryDto dto = new ExecuteQueryDto();


	@Before
	public void setup() {
		databaseRepository.saveAndFlush(mysql);
		databaseRepository.saveAndFlush(oracle);
		memberRepository.saveAndFlush(member);
	}

	@Test
	public void testExecuteQuery() throws Exception {
		// give
		dto.setId(mysql.getId());
		dto.setLoginId(member.getLoginId());
		dto.setQuery(mysql.getDriver().getValidateQuery());
		dto.setIp(InetAddress.getLocalHost().getHostAddress());
		// when
		MessageVo vo = dbclienManager.executeQuery(dto);
		// then
		assertThat(vo.getHttpStatus(),equalTo(200));

		// give
		dto.setId(oracle.getId());
		dto.setLoginId(member.getLoginId());
		dto.setQuery(oracle.getDriver().getValidateQuery());
		// when
		vo = dbclienManager.executeQuery(dto);
		// then
		// then
		assertThat(vo.getHttpStatus(),equalTo(200));

		// 실행 로그 기록을 위해 1초간 sleep.
		Thread.sleep(1000);
	}

	@Test
	public void testSelectTableVoList() throws Exception {
		// give
		dto.setId(mysql.getId());
		// when
		List<TableVo> list = dbclienManager.selectTableVoList(dto);
		// then
		assertTrue(list.size() > 0);

		// give
		dto.setId(oracle.getId());
		// when
		list = dbclienManager.selectTableVoList(dto);
		// then
		assertTrue(list.size() > 0);
	}

	@Test
	public void testSelectTableFieldVoList() throws Exception {
		// gvie
		dto.setId(mysql.getId());
		List<TableVo> tlist = dbclienManager.selectTableVoList(dto);
		dto.setName(tlist.get(0).getTableName());
		//when
		List<FieldVo> flist = dbclienManager.selectTableFieldVoList(dto);
		//then
		assertTrue(flist.size()>0);

		// give
		dto.setId(oracle.getId());
		tlist = dbclienManager.selectTableVoList(dto);
		dto.setName(tlist.get(0).getTableName());
		// when
		flist = dbclienManager.selectTableFieldVoList(dto);
		// then
		assertTrue(flist.size() > 0);

	}

	@Test
	public void testSelectTableIndexVoList() throws Exception {
		// gvie
		dto.setId(mysql.getId());
		List<TableVo> tlist = dbclienManager.selectTableVoList(dto);
		dto.setName(tlist.get(0).getTableName());
		//when
		List<IndexVo> flist = dbclienManager.selectTableIndexVoList(dto);
		//then
		assertTrue(flist.size()>0);

		// give
		dto.setId(oracle.getId());
		tlist = dbclienManager.selectTableVoList(dto);
		dto.setName(tlist.get(0).getTableName());
		// when
		flist = dbclienManager.selectTableIndexVoList(dto);
		// then
		assertTrue(flist.size() > 0);

	}

	@Test
	public void testSelectViewVoList() throws Exception {
		// gvie
		dto.setId(mysql.getId());
		//when
		List<ViewVo> flist = dbclienManager.selectViewVoList(dto);
		//then
		assertTrue(flist.size()>0);

		// give
		dto.setId(oracle.getId());
		// when
		flist = dbclienManager.selectViewVoList(dto);
		// then
		assertTrue(flist.size() > 0);
	}

	@Test
	public void testSelectViewDetailList() throws Exception {
		// gvie
		dto.setId(mysql.getId());
		List<ViewVo> tlist = dbclienManager.selectViewVoList(dto);
		dto.setName(tlist.get(0).getName());
		//when
		List<Map<String, String>> flist = dbclienManager.selectViewDetailList(dto);
		//then
		assertTrue(flist.size()>0);

		// give
		dto.setId(oracle.getId());
		tlist = dbclienManager.selectViewVoList(dto);
		dto.setName(tlist.get(0).getName());
		// when
		flist = dbclienManager.selectViewDetailList(dto);
		// then
		assertTrue(flist.size() > 0);
	}

	@Test
	public void testSelectViewVoSourceList() throws Exception {
		// gvie
		dto.setId(mysql.getId());
		List<ViewVo> tlist = dbclienManager.selectViewVoList(dto);
		dto.setName(tlist.get(0).getName());
		//when
		List<ViewVo> flist = dbclienManager.selectViewVoSourceList(dto);
		//then
		assertTrue(flist.size()>0);

		// give
		dto.setId(oracle.getId());
		tlist = dbclienManager.selectViewVoList(dto);
		dto.setName(tlist.get(0).getName());
		// when
		flist = dbclienManager.selectViewVoSourceList(dto);
		// then
		assertTrue(flist.size() > 0);
	}
}