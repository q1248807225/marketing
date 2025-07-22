package com.example.marketing.repository;

import com.example.marketing.entity.ActivityConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityConfigRepository extends JpaRepository<ActivityConfig, Long> {
    ActivityConfig findByActivityType(String activityType);
}
