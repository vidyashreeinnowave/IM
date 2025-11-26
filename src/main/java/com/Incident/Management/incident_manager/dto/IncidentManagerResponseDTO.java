package com.Incident.Management.incident_manager.dto;

import java.util.List;

import lombok.Data;

@Data
public class IncidentManagerResponseDTO {
    private String managerId;
    private String name;
    private TeamDTO team;
    private List<IncidentResponseDTO> incidents;
    private Double meanTimeToEngage;
    private Double meanTimeToResolve;
}
