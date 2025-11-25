package com.Incident.Management.incident_manager.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    // Get stats for all managers (with optional time filter)
    public List<ManagerStatsDTO> getManagerStats(Integer days, String startDate, String endDate) {
        List<IncidentManager> managers = managerRepo.findAll();
        List<ManagerStatsDTO> results = new ArrayList<>();

        for (IncidentManager m : managers) {
            List<Incident> incidents = incidentRepo.findByIncidentManager_ManagerId(m.getManagerId());

            // ----------------------
            // APPLY TIME FILTER
            // ----------------------
            if (days != null) {
                LocalDateTime threshold = LocalDateTime.now().minusDays(days);
                incidents = incidents.stream()
                        .filter(i -> i.getCreatedAt() != null && i.getCreatedAt().isAfter(threshold))
                        .collect(Collectors.toList());
            }

            if (startDate != null && endDate != null) {
                LocalDateTime start = LocalDate.parse(startDate).atStartOfDay();
                LocalDateTime end = LocalDate.parse(endDate).atTime(23, 59, 59);
                incidents = incidents.stream()
                        .filter(i -> i.getCreatedAt() != null &&
                                i.getCreatedAt().isAfter(start) &&
                                i.getCreatedAt().isBefore(end))
                        .collect(Collectors.toList());
            }

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
        List<ManagerStatsDTO> modifiable = new ArrayList<>(results);
        modifiable.sort(Comparator.comparingLong(ManagerStatsDTO::getIncidentsHandled).reversed());

        int rank = 1;
        for (ManagerStatsDTO dto : modifiable) dto.setRank(rank++);

        return modifiable;
    }

    // Get filtered stats based on KPIs + time filter
    public List<ManagerStatsDTO> getFilteredStats(
            String performanceFilter,
            String complianceFilter,
            Integer minIncidentCount,
            Integer days,
            String startDate,
            String endDate
    ) {

        // Pass time filter parameters to getManagerStats
        List<ManagerStatsDTO> stats = new ArrayList<>(getManagerStats(days, startDate, endDate));

        // Filter by minimum incident count
        if (minIncidentCount != null) {
            stats = stats.stream()
                    .filter(s -> s.getIncidentsHandled() >= minIncidentCount)
                    .collect(Collectors.toList());
        }

        // Filter by performance status
        if (performanceFilter != null && !performanceFilter.isBlank()) {
            stats = stats.stream()
                    .filter(s -> {
                        String status = "";
                        if (s.getAvgMTTR() <= 60) status = "Green";
                        else if (s.getAvgMTTR() <= 90) status = "Yellow";
                        else status = "Red";

                        return status.equalsIgnoreCase(performanceFilter);
                    })
                    .collect(Collectors.toList());
        }

        // Filter by compliance
        if (complianceFilter != null && !complianceFilter.isBlank()) {
            stats = stats.stream()
                    .filter(s -> {
                        String complianceStatus = s.getDebriefCompliance() >= 90 ? "Compliant" : "Non-Compliant";
                        return complianceStatus.equalsIgnoreCase(complianceFilter);
                    })
                    .collect(Collectors.toList());
        }

        // Re-rank after filtering
        List<ManagerStatsDTO> modifiable = new ArrayList<>(stats);
        modifiable.sort(Comparator.comparingLong(ManagerStatsDTO::getIncidentsHandled).reversed());
        int rank = 1;
        for (ManagerStatsDTO dto : modifiable) dto.setRank(rank++);

        return modifiable;
    }
}
