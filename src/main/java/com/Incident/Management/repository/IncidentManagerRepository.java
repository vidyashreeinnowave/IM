package com.Incident.Management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Incident.Management.model.IncidentManager;

public interface IncidentManagerRepository extends JpaRepository<IncidentManager, String> {

}
