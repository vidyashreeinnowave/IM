package com.Incident.Management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Incident.Management.model.IncidentStatus;

@Repository
public interface IncidentStatusRepository extends JpaRepository<IncidentStatus, Integer> {}