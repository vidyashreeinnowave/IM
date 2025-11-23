package com.Incident.Management.incident_manager.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class IncidentResponseDTO {
    private String incidentNumber;

    private String priorityCode;
    private String priorityDescription;

    private String statusName;
    private String statusDescription;

    private String managerId;
    private String managerName;

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

    private Double meanTimeToEngage;
    private Double meanTimeToResolve;

    private List<String> impactedApps;
}
