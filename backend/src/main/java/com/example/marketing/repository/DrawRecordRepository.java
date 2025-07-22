package com.example.marketing.repository;

import com.example.marketing.entity.DrawRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DrawRecordRepository extends JpaRepository<DrawRecord, Long> {
    List<DrawRecord> findByActivityTypeAndUserId(String activityType, String userId);
}
