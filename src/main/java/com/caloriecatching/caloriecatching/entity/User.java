package com.caloriecatching.caloriecatching.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "login_id", length = 45, unique = true)
    private String loginId;

    @Column(name = "password", length = 255)
    private String password;

    @Column(name = "user_name", length = 45)
    private String userName;

}
