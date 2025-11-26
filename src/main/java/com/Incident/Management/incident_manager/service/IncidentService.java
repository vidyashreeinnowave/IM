package com.Incident.Management.incident_manager.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.Incident.Management.incident_manager.dto.ImpactedApplicationResponseDTO;
import com.Incident.Management.incident_manager.dto.IncidentRequestDTO;
import com.Incident.Management.incident_manager.dto.IncidentResponseDTO;
import com.Incident.Management.incident_manager.model.Application;
import com.Incident.Management.incident_manager.model.Incident;
import com.Incident.Management.incident_manager.model.IncidentPriority;
import com.Incident.Management.incident_manager.model.IncidentStatus;
import com.Incident.Management.incident_manager.repository.ApplicationRepository;
import com.Incident.Management.incident_manager.repository.IncidentManagerRepository;
import com.Incident.Management.incident_manager.repository.IncidentPriorityRepository;
import com.Incident.Management.incident_manager.repository.IncidentRepository;
import com.Incident.Management.incident_manager.repository.IncidentStatusRepository;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepo;
    private final IncidentPriorityRepository priorityRepo;
    private final IncidentStatusRepository statusRepo;
    private final ApplicationRepository applicationRepo;
    private final IncidentManagerRepository managerRepo;

    public IncidentService(
            IncidentRepository incidentRepo,
            IncidentPriorityRepository priorityRepo,
            IncidentStatusRepository statusRepo,
            ApplicationRepository applicationRepo,
        IncidentManagerRepository managerRepo) {

        this.incidentRepo = incidentRepo;
        this.priorityRepo = priorityRepo;
        this.statusRepo = statusRepo;
        this.applicationRepo = applicationRepo;
        this.managerRepo = managerRepo;
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
        if (dto.getManagerId() != null) {
        incident.setIncidentManager(
            managerRepo.findById(dto.getManagerId())
                .orElseThrow(() -> new RuntimeException("Invalid manager ID"))
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
    private Double calculateMTTE(Incident incident) {
    if (incident.getOutageStart() == null || incident.getCrisisStart() == null) {
        return null;
    }

    long minutes = (incident.getCrisisStart().getTime() - incident.getOutageStart().getTime()) / (1000 * 60);
    return (double) minutes;
}

private Double calculateMTTR(Incident incident) {
    if (incident.getCrisisStart() == null || incident.getCrisisEnd() == null) {
        return null;
    }

    long minutes = (incident.getCrisisEnd().getTime() - incident.getCrisisStart().getTime()) / (1000 * 60);
    return (double) minutes;
}

   private IncidentResponseDTO mapToDTO(Incident incident) {

    IncidentResponseDTO dto = new IncidentResponseDTO();

    // Basic fields
    dto.setIncidentNumber(incident.getIncidentNumber());
    dto.setOutageStart(incident.getOutageStart());
    dto.setCrisisStart(incident.getCrisisStart());
    dto.setCrisisEnd(incident.getCrisisEnd());
    dto.setWarRoomLink(incident.getWarRoomLink());
    dto.setRootCauseReason(incident.getRootCauseReason());
    dto.setDebriefLink(incident.getDebriefLink());
    dto.setDebriefSummary(incident.getDebriefSummary());
    dto.setDebriefTime(incident.getDebriefTime());
    dto.setProblemTicketNumber(incident.getProblemTicketNumber());
    dto.setDebriefAttachmentPath(incident.getDebriefAttachmentPath());
    dto.setCreatedAt(incident.getCreatedAt());
    dto.setUpdatedAt(incident.getUpdatedAt());

    // Manager details
    if (incident.getIncidentManager() != null) {
        dto.setManagerId(incident.getIncidentManager().getManagerId());
        dto.setManagerName(incident.getIncidentManager().getManagerName());
    }

    // FULL PRIORITY OBJECT
    dto.setIncidentPriority(incident.getIncidentPriority());

    // FULL STATUS OBJECT
    dto.setStatus(incident.getStatus());

    // FULL ROOT CAUSE APPLICATION
    dto.setRootCauseApp(incident.getRootCauseApp());

    // FULL IMPACTED APPLICATIONS LIST
    dto.setImpactedApplications(
            incident.getImpactedApplications()
                    .stream()
                    .map(ImpactedApplicationResponseDTO::fromEntity)
                    .toList()
    );

    // Calculated fields
    dto.setMeanTimeToEngage(calculateMTTE(incident));
    dto.setMeanTimeToResolve(calculateMTTR(incident));

    return dto;
}


    public List<IncidentResponseDTO> filterIncidents(
        Integer days,
        String startDate,
        String endDate,
        List<String> priority,
        List<String> status,
        List<String> managerId,
        List<String> impactedApp,
        List<String> rootCauseApp,
        List<Integer> teamId,
        String problemTicket
) {

    List<Incident> incidents = incidentRepo.findAll();

    // Time filter: last X days
    if (days != null) {
        LocalDateTime threshold = LocalDateTime.now().minusDays(days);
        incidents = incidents.stream()
                .filter(i -> i.getCreatedAt().isAfter(threshold))
                .toList();
    }

    // Custom date range
    if (startDate != null && endDate != null) {
        LocalDateTime start = LocalDate.parse(startDate).atStartOfDay();
        LocalDateTime end = LocalDate.parse(endDate).atTime(23, 59);

        incidents = incidents.stream()
                .filter(i -> i.getCreatedAt().isAfter(start) &&
                             i.getCreatedAt().isBefore(end))
                .toList();
    }

    // Priority filter
    if (priority != null) {
        incidents = incidents.stream()
                .filter(i -> priority.contains(
                        i.getIncidentPriority().getPriorityCode()))
                .toList();
    }

    // Status filter
    if (status != null) {
        incidents = incidents.stream()
                .filter(i -> status.contains(
                        i.getStatus().getStatusName()))
                .toList();
    }

    // Manager filter
    if (managerId != null) {
        incidents = incidents.stream()
                .filter(i -> i.getIncidentManager() != null &&
                             managerId.contains(i.getIncidentManager().getManagerId()))
                .toList();
    }

    // Impacted Applications filter
    if (impactedApp != null) {
        incidents = incidents.stream()
                .filter(i -> i.getImpactedApplications().stream()
                        .anyMatch(app -> impactedApp.contains(app.getApplication().getAppName())))
                .toList();
    }

    // Root cause application
    if (rootCauseApp != null) {
        incidents = incidents.stream()
                .filter(i -> i.getRootCauseApp() != null &&
                             rootCauseApp.contains(i.getRootCauseApp().getAppName()))
                .toList();
    }

    // Team filter
    if (teamId != null) {
        incidents = incidents.stream()
                .filter(i -> i.getIncidentManager() != null &&
                             teamId.contains(i.getIncidentManager().getTeam().getTeamId()))
                .toList();
    }

    // Problem Ticket filter
    if (problemTicket != null) {
        if ("yes".equalsIgnoreCase(problemTicket)) {
            incidents = incidents.stream()
                    .filter(i -> i.getProblemTicketNumber() != null &&
                                 !i.getProblemTicketNumber().isBlank())
                    .toList();
        } else if ("no".equalsIgnoreCase(problemTicket)) {
            incidents = incidents.stream()
                    .filter(i -> i.getProblemTicketNumber() == null ||
                                 i.getProblemTicketNumber().isBlank())
                    .toList();
        }
    }

    return incidents.stream()
            .map(this::mapToDTO)
            .toList();
}

}
