package com.song7749.incident.drs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.song7749.incident.drs.domain.Member;

@Repository
public interface MemberRepository  extends JpaRepository<Member, Long> {

	Member findByLoginId(String loginId);

	Member findByLoginIdAndPassword(String loginId, String password);

}