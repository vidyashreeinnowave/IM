package com.Incident.Management.incident_manager.dto;

import com.Incident.Management.incident_manager.model.ImpactedApplication;

import lombok.Data;

@Data
public class ImpactedApplicationResponseDTO {

    private Long id;
    private String incident;
    private ApplicationDTO application;

    public static ImpactedApplicationResponseDTO fromEntity(ImpactedApplication entity) {
        ImpactedApplicationResponseDTO dto = new ImpactedApplicationResponseDTO();

        dto.setId(entity.getId());
        dto.setIncident(entity.getIncident().getIncidentNumber());

        ApplicationDTO appDTO = new ApplicationDTO();
        appDTO.setAppId(entity.getApplication().getAppId());
        appDTO.setAppName(entity.getApplication().getAppName());

        dto.setApplication(appDTO);
        return dto;
    }
}
