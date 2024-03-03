package com.ilqar.taskuserservice.controller;

import com.ilqar.taskuserservice.config.JwtProvider;
import com.ilqar.taskuserservice.dto.AuthResponse;
import com.ilqar.taskuserservice.dto.LoginRequest;
import com.ilqar.taskuserservice.dto.UserDto;
import com.ilqar.taskuserservice.modal.User;
import com.ilqar.taskuserservice.repo.UserRepo;
import com.ilqar.taskuserservice.service.CustomerUserServiceImplementation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private CustomerUserServiceImplementation customerUserServiceImplementation;

    public AuthController(UserRepo userRepo, PasswordEncoder passwordEncoder, CustomerUserServiceImplementation customerUserServiceImplementation) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.customerUserServiceImplementation = customerUserServiceImplementation;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody UserDto authRequest) {
        User user = new User();
        user.setEmail(authRequest.getEmail());
        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        user.setRole(authRequest.getRole());
        user.setFullName(authRequest.getFullName());
        User isEmailExist = userRepo.findByEmail(authRequest.getEmail());
        if (isEmailExist != null) {
            throw new RuntimeException("Email already exist");
        }
        User createdUser = userRepo.save(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(createdUser.getEmail(), createdUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = JwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("User created successfully");
        authResponse.setStatus(true);
        return ResponseEntity.ok(authResponse);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest authRequest) {
        User user = userRepo.findByEmail(authRequest.getEmail());
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Password is not correct");
        }
        Authentication authentication = authenticate(authRequest.getEmail(), authRequest.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = JwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("User logged in successfully");
        authResponse.setStatus(true);
        return ResponseEntity.ok(authResponse);
    }
    private Authentication authenticate(String email, String password) {
        UserDetails userDetails = customerUserServiceImplementation.loadUserByUsername(email);
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(email, password);
        }
        throw new RuntimeException("Password is not correct");
    }

}
