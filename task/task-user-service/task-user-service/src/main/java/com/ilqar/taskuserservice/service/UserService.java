package com.ilqar.taskuserservice.service;

import com.ilqar.taskuserservice.modal.User;

import java.util.List;

public interface UserService {
    public User getUserProfile(String jwt);
    public List<User> getAllUsers();
}
