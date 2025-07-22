package com.example.marketing.service;

import org.springframework.stereotype.Service;

@Service
public class CouponService {
    private final java.util.List<String> issued = new java.util.ArrayList<>();

    public void issueCoupon(String activityType) {
        issued.add(activityType);
        System.out.println("Issuing coupon for activity: " + activityType);
    }

    public java.util.List<String> getIssuedCoupons() {
        return issued;
    }
}
