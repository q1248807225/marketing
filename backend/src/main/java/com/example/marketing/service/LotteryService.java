package com.example.marketing.service;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class LotteryService implements JavaDelegate {
    private final Random random = new Random();

    @Override
    public void execute(DelegateExecution execution) {
        boolean won = random.nextBoolean();
        execution.setVariable("won", won);
    }
}
