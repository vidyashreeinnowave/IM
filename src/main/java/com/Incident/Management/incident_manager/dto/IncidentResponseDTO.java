package com.Incident.Management.incident_manager.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.Incident.Management.incident_manager.model.Application;
import com.Incident.Management.incident_manager.model.Incident;
import com.Incident.Management.incident_manager.model.IncidentPriority;
import com.Incident.Management.incident_manager.model.IncidentStatus;

import lombok.Data;

@Data
public class IncidentResponseDTO {

    private String incidentNumber;

    private IncidentPriority incidentPriority;
    private String incidentManager; // managerId
    private IncidentStatus status;

    private Application rootCauseApp;
    private List<ImpactedApplicationResponseDTO> impactedApplications;

    private Timestamp outageStart;
    private Timestamp crisisStart;
    private Timestamp crisisEnd;

    private String warRoomLink;
    private String rootCauseReason;
    private String debriefLink;
    private String debriefSummary;
    private Timestamp debriefTime;

    private String problemTicketNumber;
    private String debriefAttachmentPath;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String managerName;
    private String managerId;

    private Double meanTimeToEngage;
    private Double meanTimeToResolve;

    private String teamId;
    private String teamName;
    // -------------------------
    // STATIC MAPPER (IMPORTANT)
    // -------------------------
    public static IncidentResponseDTO fromEntity(Incident incident) {
        IncidentResponseDTO dto = new IncidentResponseDTO();

        dto.setIncidentNumber(incident.getIncidentNumber());
        dto.setIncidentPriority(incident.getIncidentPriority());
        dto.setIncidentManager(
                incident.getIncidentManager() != null ?
                        incident.getIncidentManager().getManagerId() : null
        );
        dto.setStatus(incident.getStatus());
        dto.setRootCauseApp(incident.getRootCauseApp());

        // Map impacted apps
        if (incident.getImpactedApplications() != null) {
            dto.setImpactedApplications(
                    incident.getImpactedApplications()
                            .stream()
                            .map(ImpactedApplicationResponseDTO::fromEntity)
                            .collect(Collectors.toList())
            );
        }

        dto.setOutageStart(incident.getOutageStart());
        dto.setCrisisStart(incident.getCrisisStart());
        dto.setCrisisEnd(incident.getCrisisEnd());
        dto.setWarRoomLink(incident.getWarRoomLink());
        dto.setRootCauseReason(incident.getRootCauseReason());
        dto.setDebriefLink(incident.getDebriefLink());
        dto.setDebriefSummary(incident.getDebriefSummary());
        dto.setDebriefTime(incident.getDebriefTime());
        dto.setProblemTicketNumber(incident.getProblemTicketNumber());
        dto.setDebriefAttachmentPath(incident.getDebriefAttachmentPath());

        dto.setCreatedAt(incident.getCreatedAt());
        dto.setUpdatedAt(incident.getUpdatedAt());

        if (incident.getIncidentManager() != null) {
            dto.setManagerName(incident.getIncidentManager().getManagerName());
            dto.setManagerId(incident.getIncidentManager().getManagerId());
            dto.setTeamId(incident.getIncidentManager().getTeam().getTeamId());
            dto.setTeamName(incident.getIncidentManager().getTeam().getTeamName());
        }

        // Compute MTTx if needed
        if (incident.getOutageStart() != null && incident.getCrisisStart() != null) {
            Double mttEngage = (double) (incident.getCrisisStart().toInstant().toEpochMilli()
                    - incident.getOutageStart().toInstant().toEpochMilli());
            dto.setMeanTimeToEngage(mttEngage / 60000); // minutes
        }

        if (incident.getOutageStart() != null && incident.getCrisisEnd() != null) {
            Double mttResolve = (double) (incident.getCrisisEnd().toInstant().toEpochMilli()
                    - incident.getOutageStart().toInstant().toEpochMilli());
            dto.setMeanTimeToResolve(mttResolve / 60000); // minutes
        }

        return dto;
    }
}
