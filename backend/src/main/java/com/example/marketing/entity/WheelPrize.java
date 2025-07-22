package com.example.marketing.entity;

import jakarta.persistence.*;

@Entity
public class WheelPrize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String activityType;

    private String prizeName;

    // Type of reward: RED_ENVELOPE, COUPON, PRODUCT
    private String prizeType;

    // Reference to reward configuration
    private Long rewardId;

    private double probability;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getActivityType() { return activityType; }
    public void setActivityType(String activityType) { this.activityType = activityType; }

    public String getPrizeName() { return prizeName; }
    public void setPrizeName(String prizeName) { this.prizeName = prizeName; }

    public String getPrizeType() { return prizeType; }
    public void setPrizeType(String prizeType) { this.prizeType = prizeType; }

    public Long getRewardId() { return rewardId; }
    public void setRewardId(Long rewardId) { this.rewardId = rewardId; }

    public double getProbability() { return probability; }
    public void setProbability(double probability) { this.probability = probability; }
}
