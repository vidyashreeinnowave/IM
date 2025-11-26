package com.Incident.Management.incident_manager.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Incident.Management.incident_manager.dto.IncidentManagerResponseDTO;
import com.Incident.Management.incident_manager.dto.IncidentResponseDTO;
import com.Incident.Management.incident_manager.dto.TeamDTO;
import com.Incident.Management.incident_manager.model.IncidentManager;
import com.Incident.Management.incident_manager.repository.IncidentManagerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IncidentManagerService {

    private final IncidentManagerRepository managerRepo;

    public List<IncidentManagerResponseDTO> getAllIncidentManagers() {
        List<IncidentManager> managers = managerRepo.findAll();
        return managers.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private IncidentManagerResponseDTO mapToDTO(IncidentManager manager) {

        IncidentManagerResponseDTO dto = new IncidentManagerResponseDTO();
        dto.setManagerId(manager.getManagerId());
        dto.setName(manager.getManagerName());

        // -------- TEAM ----------
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setTeamId(manager.getTeam().getTeamId().toString());
        teamDTO.setTeamName(manager.getTeam().getTeamName());
        dto.setTeam(teamDTO);

        // -------- INCIDENTS ----------
        List<IncidentResponseDTO> incidentDTOs = manager.getIncidents().stream()
                .map(IncidentResponseDTO::fromEntity)
                .collect(Collectors.toList());

        dto.setIncidents(incidentDTOs);

        // -------- CALCULATE MANAGER-LEVEL MTTE / MTTR -------------

        double avgMTTE = incidentDTOs.stream()
                .map(IncidentResponseDTO::getMeanTimeToEngage)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);

        double avgMTTR = incidentDTOs.stream()
                .map(IncidentResponseDTO::getMeanTimeToResolve)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);

        dto.setMeanTimeToEngage(avgMTTE);
        dto.setMeanTimeToResolve(avgMTTR);

        return dto;
    }
}
