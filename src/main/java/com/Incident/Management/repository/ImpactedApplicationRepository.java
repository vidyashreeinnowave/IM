package com.Incident.Management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Incident.Management.model.ImpactedApplication;

@Repository
public interface ImpactedApplicationRepository extends JpaRepository<ImpactedApplication, String> {


}
