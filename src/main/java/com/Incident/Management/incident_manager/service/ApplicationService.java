package com.Incident.Management.incident_manager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Incident.Management.incident_manager.model.Application;
import com.Incident.Management.incident_manager.repository.ApplicationRepository;
@Service
public class ApplicationService {
    private final ApplicationRepository repo;

    public ApplicationService(ApplicationRepository repo) {
        this.repo = repo;
    }

    public List<Application> getAll() {
        return repo.findAll();
    }
}
