package com.Incident.Management.incident_manager.dto;

import java.util.List;

import lombok.Data;

@Data
public class ManagerPriorityResponse {
    private String managerId;
    private List<PriorityItem> priority;

    @Data
    public static class PriorityItem {
        private String name;
        private Long value;

        public PriorityItem(String name, Long value) {
            this.name = name;
            this.value = value;
        }
    }
}
