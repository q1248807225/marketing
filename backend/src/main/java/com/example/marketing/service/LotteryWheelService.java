package com.example.marketing.service;

import com.example.marketing.entity.WheelPrize;
import com.example.marketing.entity.DrawRecord;
import com.example.marketing.repository.WheelPrizeRepository;
import com.example.marketing.repository.DrawRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class LotteryWheelService {
    @Autowired
    private WheelPrizeRepository prizeRepository;

    @Autowired
    private DrawRecordRepository recordRepository;

    @Autowired
    private RewardService rewardService;

    private final Random random = new Random();

    public List<WheelPrize> listPrizes(String activityType) {
        return prizeRepository.findByActivityType(activityType);
    }

    public String draw(String activityType, String userId) {
        List<WheelPrize> prizes = prizeRepository.findByActivityType(activityType);
        double r = random.nextDouble();
        double cumulative = 0.0;
        WheelPrize selected = null;
        for (WheelPrize p : prizes) {
            cumulative += p.getProbability();
            if (r <= cumulative) {
                selected = p;
                break;
            }
        }

        String prizeName = selected != null ? selected.getPrizeName() : null;
        if (selected != null && selected.getRewardId() != null) {
            rewardService.issueReward(selected.getRewardId(), userId);
        }

        DrawRecord record = new DrawRecord();
        record.setActivityType(activityType);
        record.setUserId(userId);
        record.setPrizeName(prizeName);
        record.setDrawnAt(LocalDateTime.now());
        recordRepository.save(record);
        return prizeName;
    }

    public List<DrawRecord> listRecords(String activityType, String userId) {
        return recordRepository.findByActivityTypeAndUserId(activityType, userId);
    }

    public WheelPrize savePrize(WheelPrize prize) {
        return prizeRepository.save(prize);
    }
}
