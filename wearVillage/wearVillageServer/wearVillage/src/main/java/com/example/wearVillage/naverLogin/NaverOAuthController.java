package com.example.wearVillage.naverLogin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class NaverOAuthController {

    private final naverLoginService naverLoginService;

    @GetMapping("/naver")
    public void naverLogin(HttpServletRequest request, HttpServletResponse response){
        final String loginUrl = naverLoginService.getRequestLoginUrl();
        try{
            response.sendRedirect(loginUrl);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    @GetMapping("naverLogin")
    public ResponseEntity<?> requestAccessCallback(@RequestParam(value="code") String authCode, @RequestParam(value="state") String state) {
        ResponseEntity<?> tokenResponseEntity = naverLoginService.requestAccessToken(authCode, state);

        if (tokenResponseEntity.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.badRequest().build(); // 실패하면 400 Bad Request 응답을 반환합니다.
        }

        Map<String, Object> tokenResponseBody = (Map<String, Object>) tokenResponseEntity.getBody();
        String accessToken = (String) tokenResponseBody.get("access_token");

        ResponseEntity<?> userInfoResponseEntity = naverLoginService.getUserInfo(accessToken);

        if (userInfoResponseEntity.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok(userInfoResponseEntity.getBody()); // 성공하면 200 OK 응답을 반환합니다.
        } else {
            return ResponseEntity.badRequest().build(); // 실패하면 400 Bad Request 응답을 반환합니다.
        }
    }
}
