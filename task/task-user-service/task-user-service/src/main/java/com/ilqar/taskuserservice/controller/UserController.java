package com.ilqar.taskuserservice.controller;

import com.ilqar.taskuserservice.config.JwtConstant;
import com.ilqar.taskuserservice.modal.User;
import com.ilqar.taskuserservice.service.UserService;
import com.ilqar.taskuserservice.service.imp.UserServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserServiceImp userService;

    public UserController(UserServiceImp userService) {
        this.userService = userService;
    }
    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader(JwtConstant.JWT_TOKEN_HEADER) String jwt) {

        return new ResponseEntity<>(userService.getUserProfile(jwt), HttpStatus.OK);
    }
    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers(@RequestHeader(JwtConstant.JWT_TOKEN_HEADER) String jwt) {
       List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
