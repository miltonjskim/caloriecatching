package com.caloriecatching.caloriecatching.controller;

import com.caloriecatching.caloriecatching.dto.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.caloriecatching.caloriecatching.service.UserService;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestParam String userName, @RequestParam String loginId, @RequestParam String password1, @RequestParam String password2) {

        if(!password1.equals(password2)) {
            return ResponseEntity.badRequest().body("Passwords do not match.");
        }

        userService.create(userName, loginId, password1);

        // TODO : 회원 가입 완료 페이지
        return ResponseEntity.ok("User created successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<JwtToken> login(@RequestBody Map<String, String> loginForm) {

        JwtToken token = userService.login(loginForm.get("loginId"), loginForm.get("password"));

        return ResponseEntity.ok(token);
    }

    // TODO : calorie 요청
}
