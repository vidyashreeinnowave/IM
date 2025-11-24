package com.Incident.Management.incident_manager.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Incident.Management.incident_manager.dto.IncidentRequestDTO;
import com.Incident.Management.incident_manager.dto.IncidentResponseDTO;
import com.Incident.Management.incident_manager.dto.KpiDashboardDTO;
import com.Incident.Management.incident_manager.dto.ManagerStatsDTO;
import com.Incident.Management.incident_manager.service.IncidentService;
import com.Incident.Management.incident_manager.service.KpiDashboardService;
import com.Incident.Management.incident_manager.service.ManagerStatsService;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {

    private final IncidentService incidentService;
    private final ManagerStatsService statsService;
    private final KpiDashboardService kpiService;

    public IncidentController(IncidentService incidentService, ManagerStatsService statsService, KpiDashboardService kpiService) {
    this.incidentService = incidentService;
    this.statsService = statsService;
    this.kpiService = kpiService;
}

    // GET ALL incidents (OPTIONAL pagination)
    @GetMapping
    public Object getAll(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        if (page != null && size != null) {
            return incidentService.getAllIncidents(page, size);
        }
        return incidentService.getAllIncidents();
    }

    // GET BY INCIDENT NUMBER
    @GetMapping("/{incidentNumber}")
    public IncidentResponseDTO getOne(@PathVariable String incidentNumber) {
        return incidentService.getIncident(incidentNumber);
    }

    // CREATE INCIDENT
    @PostMapping
    public IncidentResponseDTO create(@RequestBody IncidentRequestDTO dto) {
        return incidentService.createIncident(dto);
    }

    // UPDATE INCIDENT
    @PutMapping("/{incidentNumber}")
    public IncidentResponseDTO update(
            @PathVariable String incidentNumber,
            @RequestBody IncidentRequestDTO dto) {
        return incidentService.updateIncident(incidentNumber, dto);
    }

    // DELETE INCIDENT
    @DeleteMapping("/{incidentNumber}")
    public void delete(@PathVariable String incidentNumber) {
        incidentService.deleteIncident(incidentNumber);
    }

    //Stats for IM Efficinecy Leaderboard
    @GetMapping("/stats")
    public ResponseEntity<List<ManagerStatsDTO>> getStats() {
        return ResponseEntity.ok(statsService.getManagerStats());
    }

    @GetMapping("/kpi")
    public ResponseEntity<KpiDashboardDTO> getDashboard() {
        return ResponseEntity.ok(kpiService.getKpiDashboard());
    }
    @GetMapping("/filter")
public ResponseEntity<List<IncidentResponseDTO>> filterIncidents(

        // Time period
        @RequestParam(required = false) Integer days,
        @RequestParam(required = false) String startDate,
        @RequestParam(required = false) String endDate,

        // Priority
        @RequestParam(required = false) List<String> priority,

        // Status
        @RequestParam(required = false) List<String> status,

        // Incident Manager
        @RequestParam(required = false) List<String> managerId,

        // Impacted Application
        @RequestParam(required = false) List<String> impactedApp,

        // Root Cause Application
        @RequestParam(required = false) List<String> rootCauseApp,

        // Team(s)
        @RequestParam(required = false) List<Integer> teamId,

        // Problem Ticket
        @RequestParam(required = false) String problemTicket
) {
    return ResponseEntity.ok(
            incidentService.filterIncidents(days, startDate, endDate,
                    priority, status, managerId,
                    impactedApp, rootCauseApp,
                    teamId, problemTicket)
    );
}

}


