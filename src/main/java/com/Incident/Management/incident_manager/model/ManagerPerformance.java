package com.Incident.Management.incident_manager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagerPerformance {
    private int rank;
    private String managerName;
    private int avgMTTR;            // e.g., "2h 15m"
    private int avgMTTE;            // e.g., "30m"
    private int debriefCompliance_percentage;  // e.g., "98%"
    private int incidentHandling;      // score (integer)
}
