package com.Incident.Management.incident_manager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Incident.Management.incident_manager.model.Team;
import com.Incident.Management.incident_manager.repository.TeamRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }
}
