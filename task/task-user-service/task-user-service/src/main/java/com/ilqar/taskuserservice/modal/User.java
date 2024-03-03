package com.ilqar.taskuserservice.modal;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String password;
    private String email;
    private String role;
    private String fullName;
}
