package com.task.tasksubmissionservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/submission")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Welcome to Task Submission Service");
    }
}
