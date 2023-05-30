package com.caloriecatching.caloriecatching.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserTable {

    @Id
    private Integer userID;

    @Column(length = 45, unique = true)
    private String loginID;

    @Column(length = 45)
    private String password;

    @Column(length = 45)
    private String userName;
}
