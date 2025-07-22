package com.example.marketing.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ParticipationRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String activityType;

    private String processId;

    private LocalDateTime participatedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getActivityType() { return activityType; }
    public void setActivityType(String activityType) { this.activityType = activityType; }

    public String getProcessId() { return processId; }
    public void setProcessId(String processId) { this.processId = processId; }

    public LocalDateTime getParticipatedAt() { return participatedAt; }
    public void setParticipatedAt(LocalDateTime participatedAt) { this.participatedAt = participatedAt; }
}
