package com.example.wearVillage.dataContoller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@CrossOrigin(origins = {"http://localhost:8090"})
@RestController
@RequestMapping("/api")
public class ApiController {

    private final RestTemplate restTemplate;

    public ApiController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/maps")
    public ResponseEntity<String> getMapData(@RequestParam String query) {
        String naverMapsApiUrl = "https://openapi.naver.com/v1/search/local.json?query=" + query;
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", "0mTIguFzdA7z9a9TF_3h");
        headers.set("X-Naver-Client-Secret", "kbuSPbUHhJ");
        
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        ResponseEntity<String> response = this.restTemplate.exchange(naverMapsApiUrl, HttpMethod.GET, entity, String.class);
        
        return response;
    }
}