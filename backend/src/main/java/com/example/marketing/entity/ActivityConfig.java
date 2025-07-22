package com.example.marketing.entity;

import jakarta.persistence.*;

@Entity
public class ActivityConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String activityType;

    private String processKey;

    @Lob
    private String bpmnXml;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getActivityType() { return activityType; }
    public void setActivityType(String activityType) { this.activityType = activityType; }

    public String getProcessKey() { return processKey; }
    public void setProcessKey(String processKey) { this.processKey = processKey; }

    public String getBpmnXml() { return bpmnXml; }
    public void setBpmnXml(String bpmnXml) { this.bpmnXml = bpmnXml; }
}
