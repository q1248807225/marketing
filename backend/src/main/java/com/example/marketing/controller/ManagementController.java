package com.example.marketing.controller;

import org.flowable.engine.RuntimeService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.marketing.entity.ActivityConfig;
import com.example.marketing.repository.ActivityConfigRepository;
import com.example.marketing.entity.WheelPrize;
import com.example.marketing.service.LotteryWheelService;
import com.example.marketing.entity.RewardConfig;
import com.example.marketing.service.RewardService;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/management")
public class ManagementController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private com.example.marketing.service.ProcessConfigService processConfigService;

    @Autowired
    private com.example.marketing.service.ParticipationService participationService;

    @Autowired
    private ActivityConfigRepository configRepository;

    @Autowired
    private LotteryWheelService wheelService;

    @Autowired
    private RewardService rewardService;

    @PostMapping("/start")
    public Map<String, Object> startActivity(@RequestParam String activityType) {
        Map<String, Object> vars = new HashMap<>();
        vars.put("activityType", activityType);
        String key = processConfigService.getProcessKey(activityType);
        if (key == null) {
            throw new IllegalArgumentException("Process not configured for type: " + activityType);
        }

        String processId = runtimeService.startProcessInstanceByKey(key, vars).getId();
        participationService.recordParticipation("manager", activityType, processId);
        Map<String, Object> result = new HashMap<>();
        result.put("processId", processId);
        return result;
    }

    @PostMapping("/process")
    public Map<String, Object> deployProcess(@RequestParam String activityType, @RequestBody String bpmnXml) {
        processConfigService.deployProcess(activityType, bpmnXml);
        Map<String, Object> result = new HashMap<>();
        result.put("deployed", true);
        return result;
    }

    @GetMapping("/activities")
    public Iterable<ActivityConfig> listActivities() {
        return configRepository.findAll();
    }

    @PostMapping("/wheel/prize")
    public WheelPrize savePrize(@RequestParam String activityType,
                                @RequestParam String name,
                                @RequestParam String prizeType,
                                @RequestParam double probability,
                                @RequestParam(required = false) Long rewardId,
                                @RequestParam(required = false) Long id) {
        WheelPrize prize = new WheelPrize();
        prize.setId(id);
        prize.setActivityType(activityType);
        prize.setPrizeName(name);
        prize.setPrizeType(prizeType);
        prize.setProbability(probability);
        prize.setRewardId(rewardId);
        return wheelService.savePrize(prize);
    }

    @GetMapping("/wheel/prizes")
    public Iterable<WheelPrize> listPrizes(@RequestParam String activityType) {
        return wheelService.listPrizes(activityType);
    }

    @PostMapping("/reward")
    public RewardConfig saveReward(@RequestParam String rewardType,
                                   @RequestParam String name,
                                   @RequestParam String detail,
                                   @RequestParam(required = false) Long id) {
        RewardConfig cfg = new RewardConfig();
        cfg.setId(id);
        cfg.setRewardType(rewardType);
        cfg.setName(name);
        cfg.setDetail(detail);
        return rewardService.saveReward(cfg);
    }

    @GetMapping("/rewards")
    public Iterable<RewardConfig> listRewards(@RequestParam(required = false) String rewardType) {
        return rewardService.listRewards(rewardType);
    }
}
