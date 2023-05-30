package com.caloriecatching.caloriecatching.controller;

import com.caloriecatching.caloriecatching.config.FileUtils;
import com.caloriecatching.caloriecatching.service.FoodPredictionService;
import com.caloriecatching.caloriecatching.entity.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.tensorflow.TensorFlowException;
import com.caloriecatching.caloriecatching.repository.FoodRepository;

import java.io.IOException;

@RestController
@RequestMapping("/food")
public class FoodCalorieController {

    private final FoodRepository foodRepository;

    @Autowired
    public FoodCalorieController(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @PostMapping("/calorie")
    public ResponseEntity<FoodCalorieResponse> handleFileUpload(@RequestParam("file")MultipartFile file) {

        try {
            // 입력 파일을 바이트 배열로 변환
            FileUtils fileUtils = new FileUtils();
            byte[] convertedFile = fileUtils.convertMultipartFileToBytes(file);

            // 모델 서빙 api를 호출해 label값 반환
            FoodPredictionService fpService = new FoodPredictionService();
            int predictedValue = fpService.getFoodPrediction(convertedFile);

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
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (TensorFlowException e) {
            // TensorFlow 모델 관련 예외 처리
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            // 기타 예외 처리
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static class FoodCalorieResponse {

        private final String korname;
        private final int calorie;

        public FoodCalorieResponse(String korname, int calorie) {

            this.korname = korname;
            this.calorie = calorie;
        }

        // korname getter
        public String getKorname() { return korname; }
        // calorie getter
        public int getCalorie() {
            return calorie;
        }
    }
}
