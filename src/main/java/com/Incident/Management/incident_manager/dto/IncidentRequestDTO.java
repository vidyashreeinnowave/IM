package com.Incident.Management.incident_manager.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class IncidentRequestDTO {

    private Integer priorityId;
    private Integer statusId;
    private String rootCauseAppId;
    private String managerId;

    private LocalDateTime outageStart;
    private LocalDateTime crisisStart;
    private LocalDateTime crisisEnd;

    private String warRoomLink;
    private String rootCauseReason;
    private String debriefLink;
    private String debriefSummary;
    private LocalDateTime debriefTime;
    private String problemTicketNumber;
    private String teamId;
}
