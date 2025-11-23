package com.Incident.Management.incident_manager.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "incident")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Incident {

    @Id
    @Column(name = "incident_number")
    private String incidentNumber;

    @ManyToOne
    @JoinColumn(name = "incident_priority_id", nullable = false)
    private IncidentPriority incidentPriority;

    @ManyToOne
    @JoinColumn(name = "incident_manager_id")
    @JsonBackReference
    private IncidentManager incidentManager;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private IncidentStatus status;

    @ManyToOne
    @JoinColumn(name = "root_cause_app_id")
    private Application rootCauseApp;

    @OneToMany(mappedBy = "incident", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ImpactedApplication> impactedApplications = new ArrayList<>();

    private Timestamp outageStart;
    private Timestamp crisisStart;
    private Timestamp crisisEnd;
    private String warRoomLink;
    private String rootCauseReason;
    private String debriefLink;
    private String debriefSummary;
    private Timestamp debriefTime;
    private String problemTicketNumber;
    private String debriefAttachmentPath;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
