package com.task.tasksubmissionservice.service;

import com.task.tasksubmissionservice.dto.TaskDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "TASK-SERVICE" ,url = "http://localhost:8080")
public interface TaskService {
    @GetMapping("/api/v1/tasks/{id}")
    TaskDto getTaskById(@PathVariable Long id,
                                               @RequestHeader("Authorization") String token) throws Exception;
    @PutMapping("/api/v1/tasks/{id}/complete")
    TaskDto completeTask(@PathVariable Long id) throws Exception;
}
