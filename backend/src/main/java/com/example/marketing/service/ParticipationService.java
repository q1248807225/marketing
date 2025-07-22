package com.example.marketing.service;

import com.example.marketing.entity.ParticipationRecord;
import com.example.marketing.repository.ParticipationRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ParticipationService {
    @Autowired
    private ParticipationRecordRepository repository;

    public void recordParticipation(String userId, String activityType, String processId) {
        ParticipationRecord record = new ParticipationRecord();
        record.setUserId(userId);
        record.setActivityType(activityType);
        record.setProcessId(processId);
        record.setParticipatedAt(LocalDateTime.now());
        repository.save(record);
    }
}
