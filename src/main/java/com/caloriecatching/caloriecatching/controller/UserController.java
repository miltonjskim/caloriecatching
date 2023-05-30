package com.caloriecatching.caloriecatching.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.caloriecatching.caloriecatching.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> handleSignup(@RequestParam String userName, @RequestParam String loginID, @RequestParam String password1, @RequestParam String password2) {

        if(!password1.equals(password2)) {
            return ResponseEntity.badRequest().body("Passwords do not match.");
        }

        userService.create(userName, loginID, password1);

        return ResponseEntity.ok("User created successfully.");
    }

    // TODO : login 요청

    // TODO : calorie 요청
}
