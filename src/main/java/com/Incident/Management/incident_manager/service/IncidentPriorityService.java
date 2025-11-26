package com.Incident.Management.incident_manager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Incident.Management.incident_manager.model.IncidentPriority;
import com.Incident.Management.incident_manager.repository.IncidentPriorityRepository;

@Service
public class IncidentPriorityService {

    private final IncidentPriorityRepository repo;

    public IncidentPriorityService(IncidentPriorityRepository repo) {
        this.repo = repo;
    }

    public List<IncidentPriority> getAll() {
        return repo.findAll();
    }
}
