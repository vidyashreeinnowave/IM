package com.Incident.Management.incident_manager.model;
import java.util.ArrayList;
import java.util.List;

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
@Table(name = "incident_manager")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncidentManager {
@Id
    @Column(name = "manager_id", nullable = false, length = 20)
    private String managerId;

    @Column(name = "manager_name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team; // Each manager belongs to one team

    @OneToMany(mappedBy = "incidentManager", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Incident> incidents = new ArrayList<>();

}
