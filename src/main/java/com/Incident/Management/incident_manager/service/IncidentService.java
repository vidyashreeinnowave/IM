package com.Incident.Management.incident_manager.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.Incident.Management.incident_manager.dto.IncidentRequestDTO;
import com.Incident.Management.incident_manager.dto.IncidentResponseDTO;
import com.Incident.Management.incident_manager.model.Application;
import com.Incident.Management.incident_manager.model.Incident;
import com.Incident.Management.incident_manager.model.IncidentPriority;
import com.Incident.Management.incident_manager.model.IncidentStatus;
import com.Incident.Management.incident_manager.repository.ApplicationRepository;
import com.Incident.Management.incident_manager.repository.IncidentPriorityRepository;
import com.Incident.Management.incident_manager.repository.IncidentRepository;
import com.Incident.Management.incident_manager.repository.IncidentStatusRepository;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepo;
    private final IncidentPriorityRepository priorityRepo;
    private final IncidentStatusRepository statusRepo;
    private final ApplicationRepository applicationRepo;

    public IncidentService(
            IncidentRepository incidentRepo,
            IncidentPriorityRepository priorityRepo,
            IncidentStatusRepository statusRepo,
            ApplicationRepository applicationRepo) {

        this.incidentRepo = incidentRepo;
        this.priorityRepo = priorityRepo;
        this.statusRepo = statusRepo;
        this.applicationRepo = applicationRepo;
    }
    // -------------------------------
    // GET ALL (with pagination)
    // -------------------------------
    public List<IncidentResponseDTO> getAllIncidents() {
        return incidentRepo.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    public Page<IncidentResponseDTO> getAllIncidents(int page, int size) {
        return incidentRepo.findAll(PageRequest.of(page, size))
                .map(this::mapToDTO);
    }

    // -------------------------------
    // GET BY INCIDENT NUMBER
    // -------------------------------
    @SuppressWarnings("null")
    public IncidentResponseDTO getIncident(String incidentNumber) {
        Incident incident = incidentRepo.findById(incidentNumber)
                .orElseThrow(() -> new RuntimeException("Incident not found: " + incidentNumber));

        return mapToDTO(incident);
    }

    // -------------------------------
    // CREATE INCIDENT
    // -------------------------------
    public IncidentResponseDTO createIncident(IncidentRequestDTO dto) {

        IncidentPriority priority = priorityRepo.findById(dto.getPriorityId())
                .orElseThrow(() -> new RuntimeException("Invalid priority ID"));

        IncidentStatus status = statusRepo.findById(dto.getStatusId())
                .orElseThrow(() -> new RuntimeException("Invalid status ID"));

        Application rootCauseApp = null;
        if (dto.getRootCauseAppId() != null) {
            rootCauseApp = applicationRepo.findById(dto.getRootCauseAppId())
                    .orElseThrow(() -> new RuntimeException("Invalid app ID"));
        }

        Incident incident = new Incident();
        incident.setIncidentNumber(dto.getIncidentNumber());
        incident.setIncidentPriority(priority);
        incident.setStatus(status);
        incident.setRootCauseApp(rootCauseApp);

        incident.setOutageStart(dto.getOutageStart());
        incident.setCrisisStart(dto.getCrisisStart());
        incident.setCrisisEnd(dto.getCrisisEnd());
        incident.setWarRoomLink(dto.getWarRoomLink());
        incident.setRootCauseReason(dto.getRootCauseReason());
        incident.setDebriefLink(dto.getDebriefLink());
        incident.setDebriefSummary(dto.getDebriefSummary());
        incident.setDebriefTime(dto.getDebriefTime());
        incident.setProblemTicketNumber(dto.getProblemTicketNumber());

        incidentRepo.save(incident);

        return mapToDTO(incident);
    }

    // -------------------------------
    // UPDATE INCIDENT
    // -------------------------------
    public IncidentResponseDTO updateIncident(String incidentNumber, IncidentRequestDTO dto) {

        Incident incident = incidentRepo.findById(incidentNumber)
                .orElseThrow(() -> new RuntimeException("Incident not found"));

        if (dto.getPriorityId() != null) {
            incident.setIncidentPriority(
                    priorityRepo.findById(dto.getPriorityId())
                            .orElseThrow(() -> new RuntimeException("Invalid priority")));
        }

        if (dto.getStatusId() != null) {
            incident.setStatus(
                    statusRepo.findById(dto.getStatusId())
                            .orElseThrow(() -> new RuntimeException("Invalid status")));
        }

        if (dto.getRootCauseAppId() != null) {
    incident.setRootCauseApp(
        applicationRepo.findById(dto.getRootCauseAppId())
            .orElseThrow(() -> new RuntimeException("Invalid app"))
    );
}


        incident.setOutageStart(dto.getOutageStart());
        incident.setCrisisStart(dto.getCrisisStart());
        incident.setCrisisEnd(dto.getCrisisEnd());
        incident.setWarRoomLink(dto.getWarRoomLink());
        incident.setRootCauseReason(dto.getRootCauseReason());
        incident.setDebriefLink(dto.getDebriefLink());
        incident.setDebriefSummary(dto.getDebriefSummary());
        incident.setDebriefTime(dto.getDebriefTime());
        incident.setProblemTicketNumber(dto.getProblemTicketNumber());

        incidentRepo.save(incident);

        return mapToDTO(incident);
    }

    // -------------------------------
    // DELETE INCIDENT
    // -------------------------------
    @SuppressWarnings("null")
    public void deleteIncident(String incidentNumber) {
        incidentRepo.deleteById(incidentNumber);
    }

    // -------------------------------
    // ENTITY → DTO MAPPER
    // -------------------------------
    private IncidentResponseDTO mapToDTO(Incident incident) {
        IncidentResponseDTO dto = new IncidentResponseDTO();

        dto.setIncidentNumber(incident.getIncidentNumber());
        dto.setPriorityCode(incident.getIncidentPriority().getPriorityCode());
        dto.setPriorityDescription(incident.getIncidentPriority().getDescription());
        dto.setStatusName(incident.getStatus().getStatusName());
        dto.setStatusDescription(incident.getStatus().getDescription());
        dto.setManagerId(incident.getManagerId());
        dto.setManagerName(incident.getManagerName());
        dto.setOutageStart(incident.getOutageStart());
        dto.setCrisisStart(incident.getCrisisStart());
        dto.setCrisisEnd(incident.getCrisisEnd());
        dto.setWarRoomLink(incident.getWarRoomLink());
        dto.setRootCauseReason(incident.getRootCauseReason());
        dto.setDebriefLink(incident.getDebriefLink());
        dto.setDebriefSummary(incident.getDebriefSummary());
        dto.setDebriefTime(incident.getDebriefTime());
        dto.setProblemTicketNumber(incident.getProblemTicketNumber());
        dto.setMeanTimeToEngage(incident.getMeanTimeToEngage());
        dto.setMeanTimeToResolve(incident.getMeanTimeToResolve());

        dto.setImpactedApps(
    incident.getImpactedApplications().stream()
        .map(ia -> ia.getApplication().getAppName())
        .toList()
);


        return dto;
    }
}
