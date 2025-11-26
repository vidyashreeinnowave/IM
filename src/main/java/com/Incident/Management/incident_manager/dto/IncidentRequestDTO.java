package com.Incident.Management.incident_manager.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class IncidentRequestDTO {

    private String incidentNumber;
    private Integer priorityId;
    private Integer statusId;
    private String  rootCauseAppId;
    private String managerId;

    private Timestamp outageStart;
    private Timestamp crisisStart;
    private Timestamp crisisEnd;

    private String warRoomLink;
    private String rootCauseReason;
    private String debriefLink;
    private String debriefSummary;
    private Timestamp debriefTime;
    private String problemTicketNumber;
}
