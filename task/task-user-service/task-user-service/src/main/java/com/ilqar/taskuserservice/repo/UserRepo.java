package com.ilqar.taskuserservice.repo;

import com.ilqar.taskuserservice.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    public User findByEmail(String email);
}
