package com.Incident.Management.incident_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagerStatsDTO {

    private String managerId;
    private String managerName;

    private long incidentsHandled;
    private double avgMTTR;  // in minutes or hours
    private double avgMTTE;
    private double debriefCompliance; // % value

    private int rank;
}
