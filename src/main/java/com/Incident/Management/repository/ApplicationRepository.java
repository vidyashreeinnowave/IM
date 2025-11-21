package com.Incident.Management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Incident.Management.model.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, String> {}
