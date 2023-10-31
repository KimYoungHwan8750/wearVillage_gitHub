package com.example.wearVillage.testArea.findByEmailProject.Controller;

import com.example.wearVillage.DTO.GmailDto;
import com.example.wearVillage.Entity.USER_INFO;
import com.example.wearVillage.Service.EmailService;
import com.example.wearVillage.testArea.findByEmailProject.Controller.APIResponse.APIResponse;
import com.example.wearVillage.testArea.findByEmailProject.Controller.DTO.USER_INFO_DTO;
import com.example.wearVillage.testArea.findByEmailProject.Controller.Service.FindUserIdSVC;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(value = "*")
@RestController
@RequiredArgsConstructor
@Slf4j
public class KH_ProjectController {
    private final FindUserIdSVC findUserIdSVC;
    private final EmailService emailService;

    @PostMapping("/mail/find")
    public APIResponse<String> MailFind(@RequestBody String email,HttpSession session){
        log.info("Email: {}", email);
        Optional<USER_INFO> user_info;
        try {
            user_info = findUserIdSVC.FindByUserId(email);
            if(user_info.isEmpty()){
                return new APIResponse<>("").isFail().setDivMessage("조회된 결과가 없습니다.");

            } else {
                char[] str = user_info.get().getID().toCharArray();
                StringBuffer strStr= new StringBuffer();
                for (int i = 0; i < str.length; i++) {
                    if(i<str.length-2&&i>1)
                    {
                        strStr.append('*');
                    } else {
                        strStr.append(str[i]);
                    }
                }
                session.setAttribute("id",user_info.get().getID());
                return new APIResponse<>(strStr.toString()).isSuccess().setDivMessage("정상적으로 조회되었습니다.");

            }


        } catch (Exception e){
            log.info(e.getMessage());
            return new APIResponse<>("").isFail().setDivMessage("비정상적인 동작을 감지했습니다. 운영진에게 문의하세요.");
        }
    }

    @PostMapping("/mail/send2")
    public APIResponse<String> sendMail(HttpSession session, GmailDto gmailDto) throws MessagingException {
        int gmailAuthCode = emailService.sendMimeMessage(gmailDto,session);
        session.setAttribute("gmailAuthCode",gmailAuthCode);
        session.setMaxInactiveInterval(1 * 60);
        log.info("메일 송신 완료, 번호={}",gmailAuthCode);
        return new APIResponse<>("").isSuccess().setDivMessage("인증번호가 발송되었습니다.");
    }


    @PostMapping("/verify2")
    public APIResponse<String> verify(HttpSession session, @RequestBody String authCode){
        Integer sessionAuthCode = (Integer) session.getAttribute("authCode");

        Map<String, Boolean> response = new HashMap<>();

        if(sessionAuthCode != null && sessionAuthCode.equals(Integer.parseInt(authCode))){
            log.info("메일 인증 성공");
            return new APIResponse<>((String)session.getAttribute("id")).isSuccess().setDivMessage("아이디 조회에 성공했습니다.");
        } else {
            log.info("메일 인증 실패");
            return new APIResponse<>((String)session.getAttribute("")).isFail().setDivMessage("인증번호가 일치하지 않습니다.");

        }

    }

}
