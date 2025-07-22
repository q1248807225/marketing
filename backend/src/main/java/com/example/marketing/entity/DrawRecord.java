package com.example.marketing.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class DrawRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String activityType;

    private String prizeName;

    private LocalDateTime drawnAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getActivityType() { return activityType; }
    public void setActivityType(String activityType) { this.activityType = activityType; }

    public String getPrizeName() { return prizeName; }
    public void setPrizeName(String prizeName) { this.prizeName = prizeName; }

    public LocalDateTime getDrawnAt() { return drawnAt; }
    public void setDrawnAt(LocalDateTime drawnAt) { this.drawnAt = drawnAt; }
}
