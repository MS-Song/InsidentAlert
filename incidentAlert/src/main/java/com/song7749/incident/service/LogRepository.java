package com.song7749.incident.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.song7749.incident.domain.Log;

@Repository
public interface LogRepository  extends JpaRepository<Log, Long> {


}