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
                repo.save(new IncidentStatus(null, "Open", "Incident is newly created"));
                repo.save(new IncidentStatus(null, "On going", "Incident is currently in progress"));
                repo.save(new IncidentStatus(null, "Resolved", "Issue has been fixed"));
                repo.save(new IncidentStatus(null, "Closed", "Incident is closed"));

            }
        };
    }
}

