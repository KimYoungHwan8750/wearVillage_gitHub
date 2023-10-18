package com.example.wearVillage.Controller;

import com.example.wearVillage.DAO.myPagePWChangeDAO.ChangeUserPWForm;
import com.example.wearVillage.DAO.myPagePWChangeDAO.myPagePWChangeSVC;
import com.example.wearVillage.DAO.myPageProfileImageDAO.USER_INFO_FORM;
import com.example.wearVillage.DAO.myPageProfileImageDAO.myPageProfileImageSVC;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@org.springframework.stereotype.Controller
@RequiredArgsConstructor
public class pjyMyPageController {

    private final myPageProfileImageSVC ImageSVC;
    private final myPagePWChangeSVC changePWSVC;

    @GetMapping("/mypage")
    public ModelAndView myPage(ModelAndView mav, HttpSession session, USER_INFO_FORM userInfoForm, @ModelAttribute ChangeUserPWForm changeUserPWForm ){
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

    @PostMapping("/update/changePW")
    public ModelAndView changePW(HttpSession session, @ModelAttribute @Valid ChangeUserPWForm changeUserPWForm, BindingResult bindingResult){
        ModelAndView mav = new ModelAndView();
        String userId = (String) session.getAttribute("id");
        if(bindingResult.hasErrors()){
            bindingResult.rejectValue("newPW","newPW","머하냐");
            mav.addObject("changeUserPWForm", changeUserPWForm);
            mav.setViewName("myPage");
            return mav;
        }
        int row = changePWSVC.changePW(userId,changeUserPWForm);
        log.info("컨트롤러 row = {}",row);
        mav.setViewName("myPage");
        return mav;
    }
}
