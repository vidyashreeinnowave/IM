package com.Incident.Management.incident_manager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Incident.Management.incident_manager.dto.PrioritySummaryDTO;
import com.Incident.Management.incident_manager.model.Incident;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, String> {
        List<Incident> findByIncidentManager_ManagerId(String managerId);
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

