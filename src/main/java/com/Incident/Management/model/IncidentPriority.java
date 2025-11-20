package com.Incident.Management.model;


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
@Table(name = "incident_priority")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncidentPriority {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer priorityId;

    @Column(nullable = false, unique = true, length = 2)
    private String priorityCode;

    private String description;
}

