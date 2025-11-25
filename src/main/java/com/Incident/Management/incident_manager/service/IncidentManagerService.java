package com.Incident.Management.incident_manager.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Incident.Management.incident_manager.dto.IncidentManagerResponseDTO;
import com.Incident.Management.incident_manager.dto.TeamDTO;
import com.Incident.Management.incident_manager.dto.IncidentResponseDTO;
import com.Incident.Management.incident_manager.model.IncidentManager;
import com.Incident.Management.incident_manager.repository.IncidentManagerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IncidentManagerService {

    private final IncidentManagerRepository managerRepo;

    public List<IncidentManagerResponseDTO> getAllIncidentManagers() {
        List<IncidentManager> managers = managerRepo.findAll();

        return managers.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private IncidentManagerResponseDTO mapToDTO(IncidentManager manager) {
        IncidentManagerResponseDTO dto = new IncidentManagerResponseDTO();
        dto.setManagerId(manager.getManagerId());
        dto.setName(manager.getManagerName());

        // Map team
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setTeamId(manager.getTeam().getTeamId().toString());
        teamDTO.setTeamName(manager.getTeam().getTeamName());
        dto.setTeam(teamDTO);

        // Map incidents using your existing IncidentResponseDTO mapper
        List<IncidentResponseDTO> incidentDTOs =
                manager.getIncidents().stream()
                        .map(IncidentResponseDTO::fromEntity)
                        .collect(Collectors.toList());

        dto.setIncidents(incidentDTOs);

        return dto;
    }
}

