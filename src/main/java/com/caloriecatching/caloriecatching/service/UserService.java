package com.caloriecatching.caloriecatching.service;

import com.caloriecatching.caloriecatching.config.JwtTokenProvider;
import com.caloriecatching.caloriecatching.dto.JwtToken;
import com.caloriecatching.caloriecatching.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.caloriecatching.caloriecatching.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder encoder;


    public UserService(BCryptPasswordEncoder encoder, UserRepository userRepository, AuthenticationManagerBuilder authenticationManagerBuilder
                       , JwtTokenProvider jwtTokenProvider) {
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public User create(String userName, String loginId, String password) {

        User user = new User();
        user.setUserName(userName);
        user.setLoginId(loginId);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(password));

        this.userRepository.save(user);

        return user;
    }

    @Transactional
    public JwtToken login(String loginId, String password) {

        // Authenticataion 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginId, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 검증된 인증 정보로 JWT 토큰 생성
        JwtToken token = jwtTokenProvider.generateToken(authentication);

        return token;
    }
}
