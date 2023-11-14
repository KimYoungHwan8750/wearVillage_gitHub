package com.example.wearVillage.naverLogin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Map;
import java.util.logging.Logger;

@Slf4j
@Service
@RequiredArgsConstructor
public class naverLoginService implements SocialOAuthService{

    @Value("${api.naver.client_id}")
    private String naverId;

    @Value("${api.naver.client_secret}")
    private String naverSecret;

    private final String NAVER_CLIENT_ID="3Wx6ZUggUTx1VZXfbMO9";
    private final String NAVER_CLIENT_SECRET="OzMITofNs8";
    private final String NAVER_BASE_URL="https://nid.naver.com/oauth2.0/authorize";
    private final String NAVER_TOKEN_BASE_URL="https://nid.naver.com/oauth2.0/token";
    private final String NAVER_CALLBACK_URL="http://localhost:8090/naverLogin";
    @Override
    public String getRequestLoginUrl() {
        final String state = new BigInteger(130,new SecureRandom()).toString();

        MultiValueMap<String,String> requestParam = new LinkedMultiValueMap<>();
            requestParam.add("response_type","code");
            requestParam.add("state",state);
            requestParam.add("client_id",NAVER_CLIENT_ID);
            requestParam.add("redirect_uri",NAVER_CALLBACK_URL);

        return UriComponentsBuilder.fromUriString(NAVER_BASE_URL)
                .queryParams(requestParam)
                .build().encode()
                .toString();
    }

    public ResponseEntity<?> requestAccessToken(String code, String state){
        MultiValueMap<String,String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("code",code);
            requestBody.add("state",state);
            requestBody.add("client_id",NAVER_CLIENT_ID);
            requestBody.add("client_secret",NAVER_CLIENT_SECRET);
            requestBody.add("grant_type","authorization_code");

            return new RestTemplate().postForEntity(NAVER_TOKEN_BASE_URL,requestBody, Map.class);
    }

    private final String NAVER_USERINFO_URL = "https://openapi.naver.com/v1/nid/me";

    // ...

    public ResponseEntity<?> getUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(NAVER_USERINFO_URL, HttpMethod.GET, entity, Map.class);
    }
}
