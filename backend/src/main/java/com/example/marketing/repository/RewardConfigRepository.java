package com.example.marketing.repository;

import com.example.marketing.entity.RewardConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RewardConfigRepository extends JpaRepository<RewardConfig, Long> {
    List<RewardConfig> findByRewardType(String rewardType);
}
