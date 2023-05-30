package com.caloriecatching.caloriecatching.repository;

import com.caloriecatching.caloriecatching.entity.FoodTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<FoodTable, Integer> {

    FoodTable findByFoodId(Integer foodId);
}
