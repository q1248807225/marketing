package com.example.marketing.repository;

import com.example.marketing.entity.WheelPrize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WheelPrizeRepository extends JpaRepository<WheelPrize, Long> {
    List<WheelPrize> findByActivityType(String activityType);
}
