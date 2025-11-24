package com.Incident.Management.incident_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KpiDashboardDTO {
    private double avgMTTR;               // in minutes
    private String mttrRemark;            // Excellent, Good, Poor

    private double avgMTTE;               // in minutes
    private String mtteRemark;

    private double debriefCompliance;     // percentage
    private String debriefRemark;

    private double criticalHandoffCompliance;    // renamed
    private String handoffRemark;
}
