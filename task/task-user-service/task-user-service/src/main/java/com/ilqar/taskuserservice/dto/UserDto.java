package com.ilqar.taskuserservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String password;
    private String email;
    private String role;
    private String fullName;
}
