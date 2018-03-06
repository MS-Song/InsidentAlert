package com.song7749.incident.drs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.song7749.incident.drs.domain.Database;

@Repository
public interface DatabaseRepository extends JpaRepository<Database, Long> {


}