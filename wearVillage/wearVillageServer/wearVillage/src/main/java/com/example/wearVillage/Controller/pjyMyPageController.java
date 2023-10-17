package com.example.wearVillage.Controller;

import com.example.wearVillage.DAO.myPageProfileImageDAO.myPageProfileImageSVC;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@org.springframework.stereotype.Controller
@RequiredArgsConstructor
public class pjyMyPageController {

    private final myPageProfileImageSVC ImageSVC;

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
    @ResponseBody
    @GetMapping("/update/profileImg")
    public boolean myPageProfileUpload(@RequestParam String url, HttpSession session,Model model){
        String Userid = (String) session.getAttribute("id");
        log.info("UserID={}",Userid);
        log.info("url={}",url);
        int updatedRow = ImageSVC.changeProfileImage(Userid,url);
        log.info("updatedRow={}",updatedRow);
        session.removeAttribute("profileimg");
        log.info("url={}",url);
        session.setAttribute("profileimg",url);
        return true;
    }

}
