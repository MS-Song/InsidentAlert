package com.song7749.incident.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.song7749.incident.domain.Member;

@Repository
public interface MemberRepository  extends CrudRepository<Member, Long> {

}
