package com.caloriecatching.caloriecatching.service;

import com.caloriecatching.caloriecatching.entity.UserTable;
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

    public UserTable create(String userName, String loginID, String password) {

        UserTable user = new UserTable();
        user.setUserName(userName);
        user.setLoginID(loginID);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(password));

        this.userRepository.save(user);

        return user;
    }
}
