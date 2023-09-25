package com.example.wearVillage.Service;

import com.example.wearVillage.DTO.GmailDto;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {
    private JavaMailSender javaMailSender;

    public void sendSimpleMessage(GmailDto mailDto){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("wearvillage.com@gmail.com");
        message.setTo(mailDto.getAddress());
        message.setSubject("옷빌리지 인증번호 입니다.");
        message.setText("본문");
        javaMailSender.send(message);
    }
}
