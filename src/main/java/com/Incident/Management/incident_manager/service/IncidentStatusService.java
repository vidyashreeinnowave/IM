package com.Incident.Management.incident_manager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Incident.Management.incident_manager.model.IncidentStatus;
import com.Incident.Management.incident_manager.repository.IncidentStatusRepository;
@Service
public class IncidentStatusService {
    private final IncidentStatusRepository repo;

    public IncidentStatusService(IncidentStatusRepository repo) {
        this.repo = repo;
    }

    public List<IncidentStatus> getAll() {
        return repo.findAllByOrderByStatusIdAsc();
    }
}
