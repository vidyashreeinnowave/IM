package com.Incident.Management.incident_manager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Incident.Management.incident_manager.model.Incident;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, String> {
        List<Incident> findByIncidentManager_ManagerId(String managerId);
}

