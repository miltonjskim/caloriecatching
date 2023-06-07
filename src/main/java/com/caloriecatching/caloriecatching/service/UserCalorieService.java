package com.caloriecatching.caloriecatching.service;

import com.caloriecatching.caloriecatching.entity.UserDailyCalorie;
import com.caloriecatching.caloriecatching.repository.UserDailyCalorieRepository;
import com.caloriecatching.caloriecatching.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.caloriecatching.caloriecatching.entity.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserCalorieService {

    private final UserDailyCalorieRepository userDailyCalorieRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserCalorieService(UserDailyCalorieRepository userDailyCalorieRepository, UserRepository userRepository) {
        this.userDailyCalorieRepository = userDailyCalorieRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void updateUserDailyCalorie(String userName, int foodCalorie) {

        LocalDate date = LocalDate.now();

        Optional<User> userOptional = userRepository.findByUserName(userName);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // UserDailyCalorieId 생성
            UserDailyCalorie.UserDailyCalorieId id = new UserDailyCalorie.UserDailyCalorieId();
            id.setLoginId(user.getLoginId());
            id.setDate(date);

            Optional<UserDailyCalorie> userDailyCalorieOptional = userDailyCalorieRepository.findByUserDailyCalorieId(id);
            if (userDailyCalorieOptional.isPresent()) {
                UserDailyCalorie userDailyCalorie = userDailyCalorieOptional.get();
                userDailyCalorie.setDailyCalorie(userDailyCalorie.getDailyCalorie() + foodCalorie);
            } else {
                UserDailyCalorie userDailyCalorie = new UserDailyCalorie();
                userDailyCalorie.setUserDailyCalorieId(id);
                userDailyCalorie.setDailyCalorie(foodCalorie);

                userDailyCalorieRepository.save(userDailyCalorie);
            }
        } else {
            // 유저가 존재하지 않는 예외 처리
        }
    }

    @Transactional
    public List<UserDailyCalorie> getUserDailyCalorie(String userName) {

        Optional<User> userOptional = userRepository.findByUserName(userName);

        if(userOptional.isPresent()) {
            User user = userOptional.get();
            return userDailyCalorieRepository.findByUserDailyCalorieId_LoginId(user.getLoginId());
        } else {
            // TODO : 수정 필요
            return new ArrayList<>();
        }
    }
}
