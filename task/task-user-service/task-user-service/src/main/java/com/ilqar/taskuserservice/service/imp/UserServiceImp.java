package com.ilqar.taskuserservice.service.imp;

import com.ilqar.taskuserservice.config.JwtProvider;
import com.ilqar.taskuserservice.modal.User;
import com.ilqar.taskuserservice.repo.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements com.ilqar.taskuserservice.service.UserService {
private final UserRepo userRepo;

    public UserServiceImp(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User getUserProfile(String jwt) {
        String email= JwtProvider.getEmailFromToken(jwt);
            return userRepo.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
}
