package com.Incident.Management.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "application")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Application {
     @Id
    @Column(name = "app_id")
    private String appId;

    private String appName;
}
