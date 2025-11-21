package com.Incident.Management.incident_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Incident.Management.incident_manager.model.ImpactedApplication;

@Repository
public interface ImpactedApplicationRepository extends JpaRepository<ImpactedApplication, String> {


}
