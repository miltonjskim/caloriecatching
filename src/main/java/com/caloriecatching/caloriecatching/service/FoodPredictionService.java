package com.caloriecatching.caloriecatching.service;

import com.caloriecatching.caloriecatching.config.MyFlaskApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodPredictionService {

    @Autowired
    private MyFlaskApiService flaskApiService;

    public int getFoodPrediction(byte[] convertedFile) throws Exception {

        return flaskApiService.predictFood(convertedFile);
    }
}
