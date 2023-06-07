package com.caloriecatching.caloriecatching.dto;

import com.caloriecatching.caloriecatching.entity.UserDailyCalorie;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserCalorieDto {

    private LocalDate date;
    private Integer dailyCalorie;

    // UserDailyCalorie를 UserDailyCalorieDto로 변환하는 메소드
    private List<UserCalorieDto> convertToDto(List<UserDailyCalorie> userDailyCalories) {

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
