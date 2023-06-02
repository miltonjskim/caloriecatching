package com.caloriecatching.caloriecatching.controller;

import com.caloriecatching.caloriecatching.config.FileUtils;
import com.caloriecatching.caloriecatching.config.MyFlaskApiService;
import com.caloriecatching.caloriecatching.service.FoodPredictionService;
import com.caloriecatching.caloriecatching.entity.Food;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.tensorflow.TensorFlowException;
import com.caloriecatching.caloriecatching.repository.FoodRepository;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/food")
public class FoodCalorieController {

    private static final Logger logger = LoggerFactory.getLogger(FoodCalorieController.class);

    private final FoodRepository foodRepository;

    @Autowired
    public FoodCalorieController(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @PostMapping("/calorie")
    @CrossOrigin(origins = "http://localhost:5000")
    public ResponseEntity<FoodCalorieResponse> handleFileUpload(@RequestParam("image") MultipartFile image) {

        try {

            FileUtils fileUtils = new FileUtils();
            byte[] imageBytes = fileUtils.convertMultipartFileToBytes(image);

            // 모델 서빙 api를 호출해 label값 반환
            MyFlaskApiService myFlaskApiService = new MyFlaskApiService();
            int predictedValue = myFlaskApiService.predictFood(imageBytes);

            String korname = "";
            int calorie = 0;

            // predictedValue 값을 DB에 쿼리해 한글 음식명, 칼로리를 반환하는 메소드 호출
            Food food = foodRepository.findByFoodId(predictedValue);
            if(food != null) {
                korname = food.getKorName();
                calorie = food.getFoodCalorie();
            } else {
                // 음식 정보가 없는 경우 예외 처리
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // 칼로리 계산 결과를 담는 객체
            FoodCalorieResponse response = new FoodCalorieResponse(korname, calorie);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            // 파일 입출력 관련 예외 처리
            logger.error("File I/O exception occurred", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // 기타 예외 처리
            logger.error("An exception occurred", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Getter
    private static class FoodCalorieResponse {

        private final String korname;
        private final int calorie;

        public FoodCalorieResponse(String korname, int calorie) {

            this.korname = korname;
            this.calorie = calorie;
        }
    }
}
