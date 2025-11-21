package com.Incident.Management.incident_manager.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "incident_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncidentStatus {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer statusId;

    @Column(nullable = false, unique = true, length = 50)
    private String statusName;

    private String description;
}
