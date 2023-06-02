package com.caloriecatching.caloriecatching.service;

import com.caloriecatching.caloriecatching.config.MyFlaskApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

@Service
public class FoodPredictionService {

    @Autowired
    private MyFlaskApiService flaskApiService = new MyFlaskApiService();

    public int getFoodPrediction(byte[] imageBytes) throws Exception {

        return flaskApiService.predictFood(imageBytes);
    }
}
