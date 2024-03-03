package com.ilqar.taskservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping
@RestController
public class HomeController {

    @GetMapping("/tasks")
    public ResponseEntity<String> home() {
        return new ResponseEntity<>("Welcome To Task Service", HttpStatus.OK);
    }
}