package com.caloriecatching.caloriecatching.controller;

import com.caloriecatching.caloriecatching.dto.UserCalorieDto;
import com.caloriecatching.caloriecatching.dto.UserCalorieRequestDto;
import com.caloriecatching.caloriecatching.entity.User;
import com.caloriecatching.caloriecatching.entity.UserDailyCalorie;
import com.caloriecatching.caloriecatching.service.UserCalorieService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserCalorieController {

    private final UserCalorieService userCalorieService;

    @Autowired
    public UserCalorieController(UserCalorieService userCalorieService) {
        this.userCalorieService = userCalorieService;
    }

    @PostMapping("/user/calorie")
    public ResponseEntity<String> updateUserCalorie(@RequestBody UserCalorieRequestDto request) {

        String userName = request.getUserName();
        int foodCalorie = request.getFoodCalorie();

        userCalorieService.updateUserDailyCalorie(userName, foodCalorie);

        return new ResponseEntity<>("User calorie updated successfully", HttpStatus.OK);
    }

    @GetMapping("/user/calorie")
    public List<UserCalorieDto> getUserCalorie(@RequestParam(name = "userName") String userName) {

        List<UserDailyCalorie> userDailyCalories = userCalorieService.getUserDailyCalorie(userName);

        List<UserCalorieDto> userCalorieDtos = new ArrayList<>();
        for (UserDailyCalorie userDailyCalorie : userDailyCalories) {
            UserCalorieDto userCalorieDto = new UserCalorieDto();
            userCalorieDto.setDate(userDailyCalorie.getUserDailyCalorieId().getDate());
            userCalorieDto.setDailyCalorie(userDailyCalorie.getDailyCalorie());
            userCalorieDtos.add(userCalorieDto);
        }

        return userCalorieDtos;
    }
}
