package com.Incident.Management.incident_manager.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Incident.Management.incident_manager.dto.ManagerStatsDTO;
import com.Incident.Management.incident_manager.model.Incident;
import com.Incident.Management.incident_manager.model.IncidentManager;
import com.Incident.Management.incident_manager.repository.IncidentManagerRepository;
import com.Incident.Management.incident_manager.repository.IncidentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ManagerStatsService {

    private final IncidentManagerRepository managerRepo;
    private final IncidentRepository incidentRepo;

    public List<ManagerStatsDTO> getManagerStats() {

        List<IncidentManager> managers = managerRepo.findAll();
        List<ManagerStatsDTO> results = new ArrayList<>();

        for (IncidentManager m : managers) {

            List<Incident> incidents = incidentRepo.findByIncidentManager_ManagerId(m.getManagerId());

            long handled = incidents.size();

            double avgMTTR = incidents.stream()
    .filter(i -> i.getCrisisEnd() != null && i.getOutageStart() != null)
    .mapToLong(i -> Duration.between(
            i.getOutageStart().toLocalDateTime(),
            i.getCrisisEnd().toLocalDateTime()
    ).toMinutes())
    .average()
    .orElse(0);


            double avgMTTE = incidents.stream()
    .filter(i -> i.getCrisisStart() != null && i.getOutageStart() != null)
    .mapToLong(i -> Duration.between(
            i.getOutageStart().toLocalDateTime(),
            i.getCrisisStart().toLocalDateTime()
    ).toMinutes())
    .average()
    .orElse(0);


            long resolvedCount = incidents.stream()
                .filter(i -> i.getStatus() != null &&
                            "Resolved".equalsIgnoreCase(i.getStatus().getStatusName()))
                .count();

            long debriefDone = incidents.stream()
                    .filter(i -> i.getStatus() != null &&
                                "Debrief Done".equalsIgnoreCase(i.getStatus().getStatusName()))
                    .count();

            double compliance = resolvedCount == 0 ? 0 : (double) debriefDone / resolvedCount * 100;

            results.add(new ManagerStatsDTO(
                    m.getManagerId(),
                    m.getManagerName(),
                    handled,
                    avgMTTR,
                    avgMTTE,
                    compliance,
                    0 // rank assigned later
            ));
        }

        // Assign ranks by descending incident count
        results.sort(Comparator.comparingLong(ManagerStatsDTO::getIncidentsHandled).reversed());

        int rank = 1;
        for (ManagerStatsDTO dto : results) dto.setRank(rank++);

        return results;
    }
}

