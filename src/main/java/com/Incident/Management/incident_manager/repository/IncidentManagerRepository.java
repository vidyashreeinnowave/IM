package com.Incident.Management.incident_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Incident.Management.incident_manager.model.IncidentManager;

public interface IncidentManagerRepository extends JpaRepository<IncidentManager, String> {

}
