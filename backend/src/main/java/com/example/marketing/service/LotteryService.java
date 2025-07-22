package com.example.marketing.service;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

import com.example.marketing.service.LotteryWheelService;

@Service
public class LotteryService implements JavaDelegate {
    private final Random random = new Random();

    @Autowired
    private LotteryWheelService wheelService;

    @Override
    public void execute(DelegateExecution execution) {
        String activityType = (String) execution.getVariable("activityType");
        String userId = (String) execution.getVariable("userId");
        String prize = wheelService.draw(activityType, userId);
        execution.setVariable("prize", prize);
        execution.setVariable("won", prize != null);
    }
}

