package com.Incident.Management.incident_manager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Incident.Management.incident_manager.model.IncidentStatus;

@Repository
public interface IncidentStatusRepository extends JpaRepository<IncidentStatus, Integer> {
    Optional<IncidentStatus> findByStatusName(String statusName);
}