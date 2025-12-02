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

                repo.save(new IncidentStatus(null, "Active", "Incident is newly created or acknowledged"));
                repo.save(new IncidentStatus(null, "Ongoing", "Incident is currently in progress"));
                repo.save(new IncidentStatus(null, "Resolved", "Root cause fixed and incident resolved"));
                repo.save(new IncidentStatus(null, "Debrief Scheduled", "Debrief meeting has been scheduled"));
                repo.save(new IncidentStatus(null, "Debrief Done", "Debrief completed and summary documented"));
            }
        };
    }
}


