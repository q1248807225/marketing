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

    @GetMapping("/tasks")
    public List<Task> getTasks(@RequestParam String processId) {
        return taskService.createTaskQuery().processInstanceId(processId).list();
    }

    @PostMapping("/complete")
    public Map<String, Object> completeTask(@RequestParam String taskId) {
        taskService.complete(taskId);
        Map<String, Object> result = new HashMap<>();
        result.put("completed", true);
        return result;
    }
}
