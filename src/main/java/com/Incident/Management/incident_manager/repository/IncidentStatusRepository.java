package com.Incident.Management.incident_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Incident.Management.incident_manager.model.IncidentStatus;

@Repository
public interface IncidentStatusRepository extends JpaRepository<IncidentStatus, Integer> {}