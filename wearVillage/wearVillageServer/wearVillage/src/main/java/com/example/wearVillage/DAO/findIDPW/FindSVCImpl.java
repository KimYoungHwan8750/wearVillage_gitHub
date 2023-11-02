package com.example.wearVillage.DAO.findIDPW;

import com.example.wearVillage.DTO.GmailDto;
import com.example.wearVillage.Service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FindSVCImpl implements FindSVC {

    private final FindDAO findDAO;
    private final EmailService mailService;
    private final HttpSession session;

    /**
     * <span style="color:white;">박정연</span>
     * @param email findId form의 email
     * @return 찾은 ID 반환
     */
    @Override
    public String findId(String email) {
        return findDAO.findId(email);
    }

    /**
     * <span style="color:white;">박정연</span>
     * @param id findPw form의 id
     * @param email findPw form의 email
     * @return 조회 실패시 "조회 실패" 반환, 아니라면 AuthCode(문자열) 반환
     */
    @Override
    public String findPwByIdAndPw(String id, String email) {
        String findedPw = findDAO.findPwByIdAndPw(id, email);
        log.info("SVC단의 findPwByIdAndPw{}",findedPw);
        if (findedPw == "조회되는 정보가 없습니다.") {
            return "조회 실패";
        } else {
            try {
                GmailDto dto = new GmailDto();
                dto.setAddress(email);
                String AuthCode = Integer.toString(mailService.sendMimeMessage(dto, session));
                log.info("AuthCode = {}",AuthCode);
                return AuthCode;
            } catch (MessagingException e) {
                return "메일 발송 불가능";
            }
        }
    }

    @Override
    public void setTempPw(String email, String id, String pw) {
        findDAO.setTempPw(email,id,pw);
    }
}
