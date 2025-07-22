package com.example.marketing.service;

import org.flowable.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.marketing.entity.ActivityConfig;
import com.example.marketing.repository.ActivityConfigRepository;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProcessConfigService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ActivityConfigRepository configRepository;

    private final Map<String, String> activityToKey = new HashMap<>();

    @PostConstruct
    public void loadConfigs() {
        for (ActivityConfig cfg : configRepository.findAll()) {
            activityToKey.put(cfg.getActivityType(), cfg.getProcessKey());
            boolean deployed = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionKey(cfg.getProcessKey()).count() > 0;
            if (!deployed) {
                repositoryService.createDeployment()
                        .addString(cfg.getProcessKey() + ".bpmn20.xml", cfg.getBpmnXml())
                        .deploy();
            }
        }
    }

    public void deployProcess(String activityType, String bpmnXml) {
        String key = activityType + "Process";
        repositoryService.createDeployment()
                .addString(key + ".bpmn20.xml", bpmnXml)
                .deploy();
        ActivityConfig cfg = configRepository.findByActivityType(activityType);
        if (cfg == null) {
            cfg = new ActivityConfig();
            cfg.setActivityType(activityType);
        }
        cfg.setProcessKey(key);
        cfg.setBpmnXml(bpmnXml);
        configRepository.save(cfg);
        activityToKey.put(activityType, key);
    }

    public String getProcessKey(String activityType) {
        return activityToKey.get(activityType);
    }
}
