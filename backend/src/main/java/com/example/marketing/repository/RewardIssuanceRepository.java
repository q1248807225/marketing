package com.example.marketing.repository;

import com.example.marketing.entity.RewardIssuance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RewardIssuanceRepository extends JpaRepository<RewardIssuance, Long> {
    RewardIssuance findByRewardIdAndUserId(Long rewardId, String userId);
    java.util.List<RewardIssuance> findByUserId(String userId);
}
