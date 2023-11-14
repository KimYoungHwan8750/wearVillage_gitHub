package com.example.wearVillage.naverLogin;

import org.springframework.http.ResponseEntity;

public interface SocialOAuthService {
    String getRequestLoginUrl();

//    ResponseEntity<?> requestAccessToken(String code,String state);
//    void revokeToken(String accessToken);
}
