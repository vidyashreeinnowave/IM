package com.Incident.Management.incident_manager.dto;

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
    private IncidentStatus status;
    private Application rootCauseApp;
    private List<ImpactedApplicationResponseDTO> impactedApplications;

    private LocalDateTime outageStart;
    private LocalDateTime crisisStart;
    private LocalDateTime crisisEnd;

    private String warRoomLink;
    private String rootCauseReason;
    private String debriefLink;
    private String debriefSummary;
    private LocalDateTime debriefTime;

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
    // STATIC MAPPER
    // -------------------------
    public static IncidentResponseDTO fromEntity(Incident incident) {
        IncidentResponseDTO dto = new IncidentResponseDTO();

        dto.setIncidentNumber(incident.getIncidentNumber());
        dto.setIncidentPriority(incident.getIncidentPriority());
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

        // Direct assignment since entity uses LocalDateTime
        dto.setOutageStart(incident.getOutageStart());
        dto.setCrisisStart(incident.getCrisisStart());
        dto.setCrisisEnd(incident.getCrisisEnd());
        dto.setDebriefTime(incident.getDebriefTime());

        dto.setWarRoomLink(incident.getWarRoomLink());
        dto.setRootCauseReason(incident.getRootCauseReason());
        dto.setDebriefLink(incident.getDebriefMeetingLink());
        dto.setDebriefSummary(incident.getDebriefSummary());
        dto.setProblemTicketNumber(incident.getProblemTicketNumber());
        dto.setDebriefAttachmentPath(incident.getDebriefAttachmentPath());

        dto.setCreatedAt(incident.getCreatedAt());
        dto.setUpdatedAt(incident.getUpdatedAt());

        if (incident.getIncidentManager() != null) {
            dto.setManagerName(incident.getIncidentManager().getManagerName());
            dto.setManagerId(incident.getIncidentManager().getManagerId());
        }

        if (incident.getTeam() != null) {
            dto.setTeamId(incident.getTeam().getTeamId());
            dto.setTeamName(incident.getTeam().getTeamName());
        }

        // Compute MTTx in minutes
        if (dto.getOutageStart() != null && dto.getCrisisStart() != null) {
            dto.setMeanTimeToEngage(
                    (double) java.time.Duration.between(dto.getOutageStart(), dto.getCrisisStart()).toMinutes()
            );
        }

        if (dto.getOutageStart() != null && dto.getCrisisEnd() != null) {
            dto.setMeanTimeToResolve(
                    (double) java.time.Duration.between(dto.getOutageStart(), dto.getCrisisEnd()).toMinutes()
            );
        }

        return dto;
    }
}
