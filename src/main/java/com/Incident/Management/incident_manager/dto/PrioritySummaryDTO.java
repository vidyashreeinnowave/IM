package com.Incident.Management.incident_manager.dto;

import lombok.Data;

@Data
public class PrioritySummaryDTO {

    private Integer priorityId;
    private String priorityCode;

    private Long incidentCount;

    private String managerId;
    private String managerName;
    // Constructor used by JPQL query
    public PrioritySummaryDTO(Integer priorityId, String priorityCode,
                          Long incidentCount,
                          String managerId, String managerName) {
    this.priorityId = priorityId;
    this.priorityCode = priorityCode;
    this.incidentCount = incidentCount;
    this.managerId = managerId;
    this.managerName = managerName;
}

}

