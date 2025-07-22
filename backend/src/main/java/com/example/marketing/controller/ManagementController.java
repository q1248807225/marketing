package com.example.marketing.controller;

import org.flowable.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/management")
public class ManagementController {

    @Autowired
    private RuntimeService runtimeService;

    @PostMapping("/start")
    public Map<String, Object> startActivity(@RequestParam String activityType) {
        Map<String, Object> vars = new HashMap<>();
        vars.put("activityType", activityType);
        String processId = runtimeService.startProcessInstanceByKey("marketingProcess", vars).getId();
        Map<String, Object> result = new HashMap<>();
        result.put("processId", processId);
        return result;
    }
}
