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
import com.Incident.Management.incident_manager.dto.ManagerStatsDTO;
import com.Incident.Management.incident_manager.service.IncidentService;
import com.Incident.Management.incident_manager.service.ManagerStatsService;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {

    private final IncidentService incidentService;
    private final ManagerStatsService statsService;

    public IncidentController(IncidentService incidentService, ManagerStatsService statsService) {
    this.incidentService = incidentService;
    this.statsService = statsService;
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
}


