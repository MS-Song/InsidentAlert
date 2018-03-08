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

import com.song7749.base.Compare;
import com.song7749.base.Parameter;
import com.song7749.incident.drs.domain.Member;
import com.song7749.incident.drs.repository.MemberRepository;
import com.song7749.incident.drs.value.LoginDoDTO;
import com.song7749.incident.drs.value.MemberAddDto;
import com.song7749.incident.drs.value.MemberFindDto;
import com.song7749.incident.drs.value.MemberModifyDto;
import com.song7749.incident.drs.value.MemberVo;
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

	@PersistenceContext
	private EntityManager em;

	@Autowired
	ModelMapper mapper;

	@Validate
	@Override
	public MemberVo addMemeber(MemberAddDto dto) {
		return memberRepository.saveAndFlush(dto.getMember(mapper)).getMemberVo(mapper);
	}

	@Validate
	@Override
	public MemberVo modifyMember(MemberModifyDto dto) {
		// TODO 본인 확인 로직이 추가 되엉 함.

		// 조회 후 변경된 내용만 첨가 mapper config 참조
		Member member = memberRepository.findById(dto.getId()).get();
		mapper.map(dto, member);
		// 변경 일자 기록 -- last login date 와 충돌을 피가히 위해 업데이트를 별도로 기록
		member.setModifyDate(new Date(System.currentTimeMillis()));
		return memberRepository.saveAndFlush(member).getMemberVo(mapper);
	}

	@Override
	public MemberVo modifyMemberLastLoginDate(LoginDoDTO dto) {
		// 조회 후 변경된 내용만 첨가 mapper config 참조
		Member member = memberRepository.findByLoginId(dto.getLoginId());
		mapper.map(dto, member);
		// 마지막 로그인 날짜 기록
		member.setLastLoginDate(new Date(System.currentTimeMillis()));
		return memberRepository.saveAndFlush(member).getMemberVo(mapper);
	}

	@Validate
	@Override
	public void removeMember(Long id) {
		// TODO 본인 여부 확인 필요
		memberRepository.deleteById(id);
		memberRepository.flush();
	}

	@Validate
	@Override
	public MemberVo findMember(Long id) {
		Optional<Member> om = memberRepository.findById(id);
		return om.isPresent() ? om.get().getMemberVo(mapper) : null;
	}

	@Validate
	@Override
	public MemberVo findMember(String loginId) {
		Member m = memberRepository.findByLoginId(loginId);
		return m != null ? m.getMemberVo(mapper) : null ;
	}

	@Validate
	@Override
	public MemberVo findMember(String loginId, String password) {
		Member m = memberRepository.findByLoginIdAndPassword(loginId, password);
		return m != null ? m.getMemberVo(mapper) : null ;
	}


	@Validate
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

		if(StringUtils.isNoneBlank(dto.getLoginId())){
			if(dto.getLoginIdCompare().equals(Compare.LIKE)) {
				where.add("m.loginId "+ dto.getLoginIdCompare().getValue()+ " CONCAT('%',:loginId,'%')");
			} else {
				where.add("m.loginId = :loginId ");
			}
			params.add(new Parameter("loginId", dto.getLoginId()));
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