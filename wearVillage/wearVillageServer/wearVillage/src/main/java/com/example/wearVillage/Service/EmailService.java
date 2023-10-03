package com.example.wearVillage.Service;

import com.example.wearVillage.DTO.GmailDto;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
public class EmailService {
    private JavaMailSender javaMailSender;



    public int sendSimpleMessage(GmailDto mailDto, HttpSession session){
        Random randomText = new Random();
        int randomNumber = randomText.nextInt((999999-100000)+1)+100000;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("wearvillage.com@gmail.com");
        message.setTo(mailDto.getAddress());
        message.setSubject("옷빌리지 인증번호 입니다.");
        message.setText(String.valueOf(randomNumber));
        javaMailSender.send(message);

        session.setAttribute("authCode", randomNumber);

        return randomNumber;
    }


}
