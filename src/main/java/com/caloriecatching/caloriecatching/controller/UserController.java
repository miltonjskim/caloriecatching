package com.caloriecatching.caloriecatching.controller;

import com.caloriecatching.caloriecatching.service.UserSecurityService;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.AuthenticationException;
import com.caloriecatching.caloriecatching.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> handleSignup(@RequestParam String userName, @RequestParam String loginId, @RequestParam String password1, @RequestParam String password2) {

        if(!password1.equals(password2)) {
            return ResponseEntity.badRequest().body("Passwords do not match.");
        }

        userService.create(userName, loginId, password1);

        // TODO : 회원 가입 완료 페이지
        return ResponseEntity.ok("User created successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> handleLogin(@RequestBody LoginRequest loginRequest, UserSecurityService userSecurityService) {
        String loginId = loginRequest.getLoginId();
        String password = loginRequest.getPassword();

        // 로그인 요청 처리 로직
        try {
            // 사용자 인증 및 처리
            Authentication authentication = new UsernamePasswordAuthenticationToken(loginId, password);
            UserDetails userDetails = userSecurityService.loadUserByUsername(loginId);
            authentication = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 로그인 성공 시 적절한 응답 반환
            return ResponseEntity.ok("로그인 성공");
        } catch (UsernameNotFoundException e) {
            // 사용자를 찾을 수 없는 경우
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자를 찾을 수 없습니다.");
        } catch (AuthenticationException e) {
            // 인증 실패 시
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증에 실패했습니다.");
        }
    }


    @Getter
    public class  LoginRequest {

        private String loginId;
        private String password;
    }

    // TODO : calorie 요청
}