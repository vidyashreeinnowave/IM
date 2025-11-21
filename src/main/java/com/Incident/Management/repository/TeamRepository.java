package com.Incident.Management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Incident.Management.model.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, String> {}
