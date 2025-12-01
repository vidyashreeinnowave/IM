package com.Incident.Management.incident_manager.service;

import java.time.Duration;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Incident.Management.incident_manager.dto.KpiDashboardDTO;
import com.Incident.Management.incident_manager.model.Incident;
import com.Incident.Management.incident_manager.repository.IncidentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KpiDashboardService {

    private final IncidentRepository incidentRepo;

    public KpiDashboardDTO getKpiDashboard() {

        List<Incident> incidents = incidentRepo.findAll();

        // 1. MTTR
        double avgMTTR = incidents.stream()
                .filter(i -> i.getOutageStart() != null && i.getCrisisEnd() != null)
                .mapToLong(i -> Duration.between(
                        i.getOutageStart(),
                        i.getCrisisEnd()
                ).toMinutes())
                .average()
                .orElse(0);

        String mttrRemark =
                avgMTTR <= 60 ? "Excellent" :
                avgMTTR <= 90 ? "Good" :
                "Poor";

        // 2. MTTE
        double avgMTTE = incidents.stream()
                .filter(i -> i.getOutageStart() != null && i.getCrisisStart() != null)
                .mapToLong(i -> Duration.between(
                        i.getOutageStart(),
                        i.getCrisisStart()
                ).toMinutes())
                .average()
                .orElse(0);

        String mtteRemark =
                avgMTTE <= 10 ? "Excellent" :
                avgMTTE <= 15 ? "Caution" :
                "Poor";

        // 3. Debrief Compliance
        long resolved = incidents.stream()
                .filter(i -> i.getStatus() != null &&
                        "Resolved".equalsIgnoreCase(i.getStatus().getStatusName()))
                .count();

        long debriefDone = incidents.stream()
                .filter(i -> i.getStatus() != null &&
                        "Debrief Done".equalsIgnoreCase(i.getStatus().getStatusName()))
                .count();

        double debriefCompliance = resolved == 0 ? 0 :
                (double) debriefDone / resolved * 100;

        String debriefRemark =
                debriefCompliance >= 90 ? "Excellent" :
                debriefCompliance >= 80 ? "Caution" :
                "Poor";

       // 4. Critical Handoff Compliance
List<Incident> critical = incidents.stream()
        .filter(i ->
            i.getIncidentPriority() != null &&
            i.getIncidentPriority().getPriorityCode() != null &&
            (
                i.getIncidentPriority().getPriorityCode().equalsIgnoreCase("P1") ||
                i.getIncidentPriority().getPriorityCode().equalsIgnoreCase("P2")
            )
        )
        .toList();

long criticalCount = critical.size();

long handoffCount = critical.stream()
        .filter(i -> i.getProblemTicketNumber() != null &&
                     !i.getProblemTicketNumber().isBlank())
        .count();

double criticalHandoffCompliance =
        criticalCount == 0 ? 0 :
        (double) handoffCount / criticalCount * 100;

String handoffRemark =
        criticalHandoffCompliance >= 95 ? "Excellent" :
        criticalHandoffCompliance >= 85 ? "Good" :
        "Poor";



        return new KpiDashboardDTO(
                avgMTTR, mttrRemark,
                avgMTTE, mtteRemark,
                debriefCompliance, debriefRemark,
                criticalHandoffCompliance, handoffRemark
        );
    }
}
