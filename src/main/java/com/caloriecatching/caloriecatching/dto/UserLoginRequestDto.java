package com.caloriecatching.caloriecatching.dto;

import lombok.Data;

@Data
public class UserLoginRequestDto {

    private String loginId;
    private String password;
}
