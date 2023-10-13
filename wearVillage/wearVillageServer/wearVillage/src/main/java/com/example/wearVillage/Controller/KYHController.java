package com.example.wearVillage.Controller;

import com.example.wearVillage.DAO.MyPageDAO;
import com.example.wearVillage.DAO.userChatDAO;
import com.example.wearVillage.DTO.USER_INFO_DTO;
import com.example.wearVillage.Entity.USER_INFO;

import com.example.wearVillage.Repository.Repository_USER_INFO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor

public class KYHController {
    private final Repository_USER_INFO rep_user_info;

    private final userChatDAO userDAO;
    private final MyPageDAO myPageDAO;


    @GetMapping(value ="/logout")
    public String logout(HttpSession session){
        log.info((String) session.getAttribute("nickname")+"님이 로그아웃하셨습니다.");
        session.invalidate();
        return "redirect:/";
    }



    @PostMapping(value = "/chatroom")
    public String chatroom(HttpSession session){
        session.getAttribute("id");

        return "chatroom.html";
    }
    @GetMapping(value = "/chatroom")
    public String getchatroom(HttpServletRequest request){
        return "chatroom.html";
    }

    @ResponseBody
    @PostMapping(value = "/userInfo")
    public List<USER_INFO> userId(HttpSession session) throws IndexOutOfBoundsException{
        try {
            String email = (String) session.getAttribute("email");
            return rep_user_info.findByEMAIL(email);

        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping(value = "/finished_signUp")
    @ResponseBody
    public String signup(@RequestBody USER_INFO user_info) throws Exception {
        try {
            rep_user_info.save(user_info);
            System.out.println("사용자 회원가입 : "+user_info.toString());
            return "ok";
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return "no";
        }
    }

    @GetMapping(value ="/testplace")
    public String testPlace(HttpServletRequest request){
        log.info(request.getRequestURI());
        return "datatest.html";
    }
    @ResponseBody
    @PostMapping(value="/kyhtest")
    public void kyhTest(HttpServletRequest request)
    {
        log.info(request.getQueryString());

    }


    @ResponseBody
    @PostMapping("/change_password")
    public String change_password(HttpSession session,@RequestBody Map<String,Object> changeForm){
        String oldPw = (String) changeForm.get("oldPw");
        String newPw = (String) changeForm.get("newPw");
        // [현재 비밀번호]
        // [바꿀 비밀번호]
        // [바꿀 비밀번호 재입력] 의 형태로 입력받았다는 가정하에 짜인 로직이다.
        // 입력받은 현재 비밀번호와 로그인한 사용자 세션의 비밀번호가 같은 지 확인한다.
        if(session.getAttribute("pw").equals(oldPw)){
            //changePassword 실행후 변경한 비밀번호가 존재하면 true, 그렇지 않으면 false반환
            if(myPageDAO.changePassword(String.valueOf(session.getAttribute("id")),String.valueOf(session.getAttribute("pw")),newPw)){
                return "비밀번호가 변경되었습니다.";
            }else{
                //상위 if문이 실행된 이상 이 블럭에 도달할 일은 없기 때문에 예기치 않은 오류 발생.
                //해당 문구가 확인되면 점검이 필요함.
                return "비밀번호 변경에 실패했습니다. 서버에 문의해주세요";
            }
        } else {
            // 사용자가 입력한 비밀번호가 현재 세션에 담긴 비밀번호랑 일치하지 않을 때
            return "현재 비밀번호가 일치하지 않습니다. 재입력해주세요.";
        }
    }

}

