package com.Incident.Management.incident_manager.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.Incident.Management.incident_manager.model.IncidentStatus;
import com.Incident.Management.incident_manager.repository.IncidentStatusRepository;

@Configuration
public class IncidentStatusLoader {

    @Bean
    CommandLineRunner loadStatuses(IncidentStatusRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(new IncidentStatus(null, "OPEN", "Incident is newly created"));
                repo.save(new IncidentStatus(null, "IN_PROGRESS", "Being investigated"));
                repo.save(new IncidentStatus(null, "RESOLVED", "Issue has been fixed"));
                repo.save(new IncidentStatus(null, "CLOSED", "Incident is closed"));
            }
        };
    }
}

