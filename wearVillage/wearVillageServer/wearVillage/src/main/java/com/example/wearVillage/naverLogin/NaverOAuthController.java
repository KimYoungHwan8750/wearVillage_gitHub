package com.example.wearVillage.naverLogin;

import com.example.wearVillage.Entity.USER_INFO;
import com.example.wearVillage.Repository.Repository_USER_INFO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class NaverOAuthController {

    private final naverLoginService naverLoginService;
    private final Repository_USER_INFO repositoryUserInfo;

    @GetMapping("/naver")
    public void naverLogin(HttpServletRequest request, HttpServletResponse response){
        final String loginUrl = naverLoginService.getRequestLoginUrl();
        try{
            response.sendRedirect(loginUrl);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    @GetMapping("/naverLogin")
    public ResponseEntity<?> requestAccessCallback(@RequestParam(value="code") String authCode, @RequestParam(value="state") String state) {
        ResponseEntity<?> tokenResponseEntity = naverLoginService.requestAccessToken(authCode, state);

        if (tokenResponseEntity.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.badRequest().build(); // 실패하면 400 Bad Request 응답을 반환합니다.
        }

        Map<String, Object> tokenResponseBody = (Map<String, Object>) tokenResponseEntity.getBody();
        String accessToken = (String) tokenResponseBody.get("access_token");

        ResponseEntity<?> userInfoResponseEntity = naverLoginService.getUserInfo(accessToken);
        Map<String, Object> body = (Map<String, Object>) userInfoResponseEntity.getBody();
        Map<String, Object> response = (Map<String, Object>) body.get("response");
        if (response != null) {
            Object email = response.get("email");
            String email1 = email.toString();
            log.info("Email: {}", email);
            if(repositoryUserInfo.existsByEMAIL(email1)){
                List<USER_INFO> user_info;
                user_info = repositoryUserInfo.findByEMAIL(email1);
                String userId = user_info.get(0).getID();
                String userPw = user_info.get(0).getPW();

                    String id = userId;
                    String password = userPw;
                    String login = "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script><script>" +
                            "var id = '" + id + "';" +
                            "var password = '" + password + "';" +
                            "$.ajax({" +
                            "    url: '/Dologin'," +
                            "    data: JSON.stringify({ 'id': id, 'password': password })," +
                            "    type: 'POST'," +
                            "    contentType: 'application/json'," +
                            "    success: function (result) {" +
                            "        var email = result[0]['EMAIL'];" +
                            "        $.ajax({" +
                            "            url: '/login_createSession'," +
                            "            data: JSON.stringify({ 'email': email })," +
                            "            type: 'POST'," +
                            "            contentType: 'application/json'," +
                            "            success: function (result) {" +
                            "                console.log(email + '세션 등록');" +
                            "                window.location.href = '/';" +
                            "            }" +
                            "        });" +
                            "    }," +
                            "    error: function (error) {" +
                            "        console.log(error);" +
                            "    }" +
                            "});" +
                            "</script>";
                    return ResponseEntity.ok().body(login);
            } else {
                String create = "<script>window.location.href='/createUser';</script>";
                return ResponseEntity.ok().body(create);
            }
        }


        if (userInfoResponseEntity.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok(userInfoResponseEntity.getBody()); // 성공하면 200 OK 응답을 반환합니다.
        } else {
            return ResponseEntity.badRequest().build(); // 실패하면 400 Bad Request 응답을 반환합니다.
        }
    }
}
