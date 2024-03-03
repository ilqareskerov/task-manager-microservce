package com.task.tasksubmissionservice.service;

import com.task.tasksubmissionservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "USER-SERVICE" ,url = "http://localhost:8081")
public interface UserService {
    @GetMapping("/api/user/profile")
    UserDto getUserProfile(@RequestHeader("Authorization") String jwt);
}
