package com.song7749.incident.drs.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.song7749.base.Compare;
import com.song7749.base.Parameter;
import com.song7749.incident.drs.domain.Database;
import com.song7749.incident.drs.domain.Member;
import com.song7749.incident.drs.domain.MemberDatabase;
import com.song7749.incident.drs.repository.DatabaseRepository;
import com.song7749.incident.drs.repository.MemberRepository;
import com.song7749.incident.drs.type.MemberModifyByAdminDto;
import com.song7749.incident.drs.value.MemberAddDto;
import com.song7749.incident.drs.value.MemberFindDto;
import com.song7749.incident.drs.value.MemberModfyDatabaseDto;
import com.song7749.incident.drs.value.MemberModifyDto;
import com.song7749.incident.drs.value.MemberVo;
import com.song7749.incident.exception.MemberNotFoundException;
import com.song7749.util.validate.Validate;

/**
 * <pre>
 * Class Name : MemberManager.java
 * Description : 회원 관련 기능을 구현한 구현체
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 2. 25.		song7749@gmail.com		NEW
 *
 * </pre>
 *
 * @author song7749@gmail.com
 * @since 2018. 2. 25.
 */
@Service
public class MemberManagerImpl implements MemberManager {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	DatabaseRepository databaseRepository;

	@PersistenceContext
	private EntityManager em;

	@Autowired
	ModelMapper mapper;

	@Validate
	@Transactional
	@Override
	public MemberVo addMemeber(MemberAddDto dto) {
		return memberRepository.saveAndFlush(dto.getMember(mapper)).getMemberVo(mapper);
	}

	@Validate
	@Transactional
	@Override
	public MemberVo modifyMember(MemberModifyDto dto) {
		// 조회 후 변경된 내용만 첨가 mapper config 참조
		Member member = memberRepository.findById(dto.getId()).get();
		if(null == member) {
			throw new MemberNotFoundException();
		}

		// 관리자 수정시에는 변경일을 기록하지 않는다.
		if(dto instanceof MemberModifyByAdminDto) {
			mapper.map(dto, member);
		} else {
			mapper.map(dto, member);
			// 회원 정보 변경 일자 기록
			member.setModifyDate(new Date(System.currentTimeMillis()));
		}

		// 변경에 대한 로그를 기록 해야 한다.
		return memberRepository.saveAndFlush(member).getMemberVo(mapper);
	}

	@Validate
	@Transactional
	@Override
	public MemberVo modifyMember(MemberModifyByAdminDto dto) {
		return modifyMember((MemberModifyDto)dto);
	}

	@Validate
	@Transactional
	@Override
	public MemberVo modifyMember(MemberModfyDatabaseDto dto) {
		Member m = memberRepository.findById(dto.getMemberId()).get();
		Database database = databaseRepository.findById(dto.getDatebaseId()).get();

		if(null==dto.getMemberDatabaseId()) {
			m.addMemberDatabaseList(new MemberDatabase(database));
		} else {
			MemberDatabase removeMd = m.getMemberDatabaseList().stream()
				.filter(md -> dto.getMemberDatabaseId().equals(md.getId()))
				.findAny().orElse(null);
			m.getMemberDatabaseList().remove(removeMd);
		}
		memberRepository.saveAndFlush(m);
		return m.getMemberVo(mapper);
	}

	@Validate
	@Transactional
	@Override
	public void removeMember(Long id) {
		memberRepository.deleteById(id);
		memberRepository.flush();
	}

	@Validate
	@Transactional(readOnly=true)
	@Override
	public MemberVo findMember(Long id) {
		Optional<Member> om = memberRepository.findById(id);
		return om.isPresent() ? om.get().getMemberVo(mapper) : null;
	}

	@Validate
	@Transactional(readOnly=true)
	@Override
	public MemberVo findMember(String loginId) {
		Member m = memberRepository.findByLoginId(loginId);
		return m != null ? m.getMemberVo(mapper) : null ;
	}

	@Validate
	@Transactional(readOnly=true)
	@Override
	public MemberVo findMember(String loginId, String password) {
		Member m = memberRepository.findByLoginIdAndPassword(loginId, password);
		return m != null ? m.getMemberVo(mapper) : null ;
	}


	@Validate
	@Transactional(readOnly=true)
	@Override
	public List<MemberVo> findMemberList(MemberFindDto dto) {
		StringBuffer from  = new StringBuffer();
		StringJoiner where  = new StringJoiner(" and ");
		List<Parameter> params = new ArrayList<Parameter>();

		from.append("select m from Member m ");

		if(null != dto.getId()) {
			where.add("m.id= :id ");
			params.add(new Parameter("id", dto.getId()));
		}

		if(StringUtils.isNoneBlank(dto.getCertificationKey())) {
			where.add("m.certificationKey = :certificationKey ");
			params.add(new Parameter("certificationKey", dto.getCertificationKey()));
		}

		if(StringUtils.isNoneBlank(dto.getLoginId())){
			if(dto.getLoginIdCompare().equals(Compare.LIKE)) {
				where.add("m.loginId "+ dto.getLoginIdCompare().getValue()+ " CONCAT('%',:loginId,'%')");
			} else {
				where.add("m.loginId = :loginId ");
			}

		}

		if(StringUtils.isNoneBlank(dto.getName())){
			if(dto.getNameCompare().equals(Compare.LIKE)) {
				where.add("m.name "+ dto.getNameCompare().getValue()+ " CONCAT('%',:name,'%')");
			} else {
				where.add("m.name = :name ");
			}
			params.add(new Parameter("name", dto.getName()));
		}

		if(StringUtils.isNoneBlank(dto.getTeamName())) {
			if(dto.getTeamNameCompare().equals(Compare.LIKE)) {
				where.add("m.teamName "+ dto.getTeamNameCompare().getValue()+ " CONCAT('%',:teamName,'%')");
			} else {
				where.add("m.teamName = :teamName ");
			}
			params.add(new Parameter("teamName", dto.getTeamName()));
		}


		StringBuffer query = new StringBuffer();
		query.append(from.toString());
		if(where.length()>0) {
			query.append("where " + where.toString());
		}

		TypedQuery<Member> result = em.createQuery(query.toString(),Member.class);
		for(Parameter p  : params) {
			result.setParameter(p.getName(), p.getValue());
		}

		// 한정자 추가
		result.setFirstResult(dto.getOffset().intValue())
				.setMaxResults(dto.getLimit().intValue());

		List<MemberVo> mv = new ArrayList<MemberVo>();
		result.getResultList().forEach(m -> mv.add(m.getMemberVo(mapper)));

		return mv;
	}
}