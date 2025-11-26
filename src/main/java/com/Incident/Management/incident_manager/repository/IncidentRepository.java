package com.Incident.Management.incident_manager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Incident.Management.incident_manager.dto.PrioritySummaryDTO;
import com.Incident.Management.incident_manager.model.Incident;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, String> {

    // 1. Search by manager name
    List<Incident> findByIncidentManager_ManagerNameContainingIgnoreCase(String managerName);

    // 2. Search by manager id
    List<Incident> findByIncidentManager_ManagerId(String managerId);

    // 3. Search by incident number
    List<Incident> findByIncidentNumberContainingIgnoreCase(String incidentNumber);

    // 4. Search by impacted application name
    List<Incident> findByImpactedApplications_Application_AppNameContainingIgnoreCase(String appName);

    // 5. Search by impacted application ID
    List<Incident> findByImpactedApplications_Application_AppId(String appId);

    // 6. Search incidents by manager's team
    List<Incident> findByIncidentManager_Team_TeamNameContainingIgnoreCase(String teamName);


    // OPTIONAL — Flexible Multi-field Search
    @Query("""
        SELECT i FROM Incident i
        LEFT JOIN i.impactedApplications ia
        LEFT JOIN ia.application app
        WHERE
              LOWER(i.incidentNumber) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(i.incidentManager.managerName) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(i.incidentManager.managerId) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(app.appName) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(i.incidentManager.team.teamName) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    List<Incident> globalSearch(String keyword);
        @Query("""
    SELECT new com.Incident.Management.incident_manager.dto.PrioritySummaryDTO(
        p.priorityId,
        p.priorityCode,
        COUNT(i),
        m.managerId,
        m.managerName
    )
    FROM Incident i
    JOIN i.incidentPriority p
    LEFT JOIN i.incidentManager m
    GROUP BY p.priorityId, p.priorityCode, m.managerId, m.managerName
""")
List<PrioritySummaryDTO> getPrioritySummary();

}

