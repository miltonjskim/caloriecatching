package com.caloriecatching.caloriecatching.repository;

import com.caloriecatching.caloriecatching.entity.UserDailyCalorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDailyCalorieRepository extends JpaRepository<UserDailyCalorie, UserDailyCalorie.UserDailyCalorieId> {

    Optional<UserDailyCalorie> findByUserDailyCalorieId(UserDailyCalorie.UserDailyCalorieId userDailyCalorieId);

    List<UserDailyCalorie> findByUserDailyCalorieId_LoginId(String loginId);
}
