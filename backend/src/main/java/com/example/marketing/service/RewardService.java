package com.example.marketing.service;

import com.example.marketing.entity.RewardConfig;
import com.example.marketing.entity.RewardIssuance;
import com.example.marketing.repository.RewardConfigRepository;
import com.example.marketing.repository.RewardIssuanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RewardService {
    @Autowired
    private RewardConfigRepository configRepository;

    @Autowired
    private RewardIssuanceRepository issuanceRepository;

    public RewardConfig saveReward(RewardConfig config) {
        return configRepository.save(config);
    }

    public List<RewardConfig> listRewards(String type) {
        if (type == null) {
            return configRepository.findAll();
        }
        return configRepository.findByRewardType(type);
    }

    public RewardIssuance issueReward(Long rewardId, String userId) {
        RewardIssuance existing = issuanceRepository.findByRewardIdAndUserId(rewardId, userId);
        if (existing != null) {
            return existing;
        }
        RewardIssuance record = new RewardIssuance();
        record.setRewardId(rewardId);
        record.setUserId(userId);
        record.setIssuedAt(LocalDateTime.now());
        return issuanceRepository.save(record);
    }

    public List<RewardIssuance> listIssued(String userId) {
        return issuanceRepository.findByUserId(userId);
    }
}
