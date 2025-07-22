package com.example.marketing.entity;

import jakarta.persistence.*;

@Entity
public class RewardConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rewardType; // RED_ENVELOPE, COUPON, PRODUCT

    private String name;

    private String detail;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRewardType() { return rewardType; }
    public void setRewardType(String rewardType) { this.rewardType = rewardType; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDetail() { return detail; }
    public void setDetail(String detail) { this.detail = detail; }
}
