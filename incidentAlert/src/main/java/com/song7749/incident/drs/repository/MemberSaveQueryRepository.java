package com.song7749.incident.drs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.song7749.incident.drs.domain.MemberSaveQuery;

public interface MemberSaveQueryRepository   extends JpaRepository<MemberSaveQuery, Long> {

}
