package com.example.marketing.service;

import org.springframework.stereotype.Service;

@Service
public class CouponService {
    public void issueCoupon(String activityType) {
        // TODO: implement integration with coupon system
        System.out.println("Issuing coupon for activity: " + activityType);
    }
}
