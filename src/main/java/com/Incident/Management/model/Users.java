package com.Incident.Management.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @Column(name = "per_id")
    private String perId;

    @Column(name = "full_name")
    private String fullName;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String email;
}
enum Role {
    incident_manager,
    coe_lead
}
