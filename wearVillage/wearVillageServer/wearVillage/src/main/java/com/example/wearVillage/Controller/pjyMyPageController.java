package com.example.wearVillage.Controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@org.springframework.stereotype.Controller
public class pjyMyPageController {
    @GetMapping("/mypage")
    public ModelAndView myPage(ModelAndView mav, HttpSession session){
            if(session.getAttribute("email")!=null){
                String nickname = (String) session.getAttribute("nickname");
                log.info("{}'님이 로그인했습니다.'",nickname);
                mav.setViewName("myPage");
                return mav;
            }else{
                mav.setViewName("login");
                return mav;
            }
    }
}
