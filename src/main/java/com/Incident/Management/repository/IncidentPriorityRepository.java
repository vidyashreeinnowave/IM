package com.Incident.Management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Incident.Management.model.IncidentPriority;

@Repository
public interface IncidentPriorityRepository extends JpaRepository<IncidentPriority, Integer> {}
