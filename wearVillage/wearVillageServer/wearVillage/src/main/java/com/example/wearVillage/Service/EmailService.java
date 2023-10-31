package com.example.wearVillage.Service;

import com.example.wearVillage.DTO.GmailDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
public class EmailService {
    private JavaMailSender javaMailSender;



    public int sendMimeMessage(GmailDto mailDto, HttpSession session) throws MessagingException {
        Random randomText = new Random();
        int randomNumber = randomText.nextInt((999999-100000)+1)+100000;

//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("옷빌리지 <wearvillage.com@gmail.com>");
//        message.setTo(mailDto.getAddress());
//        message.setSubject("옷빌리지 인증번호 입니다.");
//        message.setText("인증번호는 "+String.valueOf(randomNumber)+" 입니다.");
//        javaMailSender.send(message);
//
//        session.setAttribute("authCode", randomNumber);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "utf-8");

        String htmlContent = "<p style='color:red; background-color:blue;'>옷빌리지 인증번호 입니다.</p><h2 style='color:blue; background-color:red;'>" + randomNumber + "</h2>";

        message.setFrom("옷빌리지 <wearvillage.com@gmail.com>");
        message.setTo(mailDto.getAddress());
        message.setSubject("옷빌리지 인증번호 입니다.");
        message.setText(htmlContent, true);

        javaMailSender.send(mimeMessage);

        session.setAttribute("authCode", randomNumber);

        return randomNumber;
    }


}