package com.Incident.Management.incident_manager.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.Incident.Management.incident_manager.model.IncidentPriority;
import com.Incident.Management.incident_manager.repository.IncidentPriorityRepository;

@Configuration
public class IncidentPriorityLoader {

    @Bean
    CommandLineRunner loadPriorities(IncidentPriorityRepository repo) {
        return args -> {

            if (repo.count() == 0) {
                repo.save(new IncidentPriority(null, "P1", "Critical priority"));
                repo.save(new IncidentPriority(null, "P2", "High priority"));
                repo.save(new IncidentPriority(null, "P3", "Medium priority"));
                repo.save(new IncidentPriority(null, "P4", "Low priority"));
            }
        };
    }
}