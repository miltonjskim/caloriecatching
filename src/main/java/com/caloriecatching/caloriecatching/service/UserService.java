package com.caloriecatching.caloriecatching.service;

import com.caloriecatching.caloriecatching.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.caloriecatching.caloriecatching.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User create(String userName, String loginId, String password) {

        User user = new User();
        user.setUserName(userName);
        user.setLoginId(loginId);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(password));

        this.userRepository.save(user);

        return user;
    }
}
