package com.Incident.Management.incident_manager.dto;

import java.time.LocalDateTime;

public class IncidentUpdateRequest {

    private String status;
    private String debriefMeetingLink;
    private String debriefSummary;
    private LocalDateTime debriefTime;
    private String problemTicketNumber;

    public IncidentUpdateRequest() {}

    // Getters and Setters

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDebriefMeetingLink() {
        return debriefMeetingLink;
    }

    public void setDebriefMeetingLink(String debriefMeetingLink) {
        this.debriefMeetingLink = debriefMeetingLink;
    }

    public String getDebriefSummary() {
        return debriefSummary;
    }

    public void setDebriefSummary(String debriefSummary) {
        this.debriefSummary = debriefSummary;
    }

    public LocalDateTime getDebriefTime() {
        return debriefTime;
    }

    public void setDebriefTime(LocalDateTime debriefTime) {
        this.debriefTime = debriefTime;
    }

    public String getProblemTicketNumber() {
        return problemTicketNumber;
    }

    public void setProblemTicketNumber(String problemTicketNumber) {
        this.problemTicketNumber = problemTicketNumber;
    }

}
