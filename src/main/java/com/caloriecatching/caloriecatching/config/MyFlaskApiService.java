package com.caloriecatching.caloriecatching.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MyFlaskApiService {

    private final RestTemplate restTemplate;

    public MyFlaskApiService() {
        this.restTemplate = new RestTemplate();
    }

    public int predictFood(byte[] originalImage) {

        int predictedValue = -1;

        // Flask api의 URL
        String apiUrl = "http://127.0.0.1:5000/api/food/calorie";

        // Request 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        // Request 바디 설정
        HttpEntity<byte[]> requestEntity = new HttpEntity<>(originalImage, headers);

        // Flask api 호출
        ResponseEntity<String> responseEntity;
        try {
            responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);
        } catch (Exception e) {
            // api 호출 실패 처리
            Logger logger = LoggerFactory.getLogger(MyFlaskApiService.class);
            logger.error("Failed to call Flask API: {}", e.getMessage());
            return predictedValue;
        }

        // 응답 결과 처리
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String responseBody = responseEntity.getBody();

            // JSON 파싱
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                JsonNode labelsNode = jsonNode.get("labels");
                if (labelsNode != null) {
                    predictedValue = labelsNode.asInt();
                }
            } catch (JsonProcessingException e) {
                // JSON 파싱 오류 처리
                Logger logger = LoggerFactory.getLogger(MyFlaskApiService.class);
                logger.error("Failed to parse JSON response: {}", e.getMessage());
            }
        } else {
            // api로부터의 비정상 응답 처리
            Logger logger = LoggerFactory.getLogger(MyFlaskApiService.class);
            logger.error("Flask API returned non-success status code: {}", responseEntity.getStatusCode());
        }

        return predictedValue;
    }
}