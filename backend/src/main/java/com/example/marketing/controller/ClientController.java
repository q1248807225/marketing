package com.example.marketing.controller;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.marketing.service.ProcessConfigService;
import com.example.marketing.service.ParticipationService;
import com.example.marketing.service.LotteryWheelService;
import com.example.marketing.entity.WheelPrize;
import com.example.marketing.entity.DrawRecord;
import com.example.marketing.entity.RewardIssuance;
import com.example.marketing.service.RewardService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private ProcessConfigService processConfigService;

    @Autowired
    private ParticipationService participationService;

    @Autowired
    private LotteryWheelService wheelService;

    @Autowired
    private RewardService rewardService;


    @GetMapping("/tasks")
    public List<Task> getTasks(@RequestParam String processId) {
        return taskService.createTaskQuery().processInstanceId(processId).list();
    }

    @PostMapping("/start")
    public Map<String, Object> start(@RequestParam String activityType, @RequestParam String userId) {
        String key = processConfigService.getProcessKey(activityType);
        if (key == null) {
            throw new IllegalArgumentException("Process not configured for type: " + activityType);
        }
        Map<String, Object> vars = new HashMap<>();
        vars.put("activityType", activityType);
        vars.put("userId", userId);
        String pid = runtimeService.startProcessInstanceByKey(key, vars).getId();
        participationService.recordParticipation(userId, activityType, pid);
        Map<String, Object> result = new HashMap<>();
        result.put("processId", pid);
        return result;
    }

    @PostMapping("/complete")
    public Map<String, Object> completeTask(@RequestParam String taskId) {
        taskService.complete(taskId);
        Map<String, Object> result = new HashMap<>();
        result.put("completed", true);
        return result;
    }

    @GetMapping("/wheel")
    public List<WheelPrize> getWheel(@RequestParam String activityType) {
        return wheelService.listPrizes(activityType);
    }

    @PostMapping("/draw")
    public Map<String, Object> draw(@RequestParam String activityType,
                                    @RequestParam String userId) {
        String prize = wheelService.draw(activityType, userId);
        Map<String, Object> result = new HashMap<>();
        result.put("prize", prize);
        return result;
    }

    @GetMapping("/draw-records")
    public List<DrawRecord> listRecords(@RequestParam String activityType,
                                        @RequestParam String userId) {
        return wheelService.listRecords(activityType, userId);
    }

    @PostMapping("/reward/issue")
    public RewardIssuance issueReward(@RequestParam Long rewardId,
                                      @RequestParam String userId) {
        return rewardService.issueReward(rewardId, userId);
    }

    @GetMapping("/reward/records")
    public List<RewardIssuance> rewardRecords(@RequestParam String userId) {
        return rewardService.listIssued(userId);
    }
}
