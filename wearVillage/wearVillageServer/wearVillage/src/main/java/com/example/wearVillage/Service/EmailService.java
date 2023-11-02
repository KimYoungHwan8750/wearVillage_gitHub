package com.example.wearVillage.Service;

import com.example.wearVillage.DTO.GmailDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
public class EmailService {
    private JavaMailSender javaMailSender;


    public int sendMimeMessage(GmailDto mailDto, HttpSession session) throws MessagingException {
        Random randomText = new Random();
        int randomNumber = randomText.nextInt((999999 - 100000) + 1) + 100000;
        String imageSrc = "http://localhost:8090/img/img_wearVillageSmallLogo.png";


        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "utf-8");

        String htmlContent = "    <div class=\"grid\" style=\"display: grid; justify-content: center\">\n" +
                "      <div\n" +
                "        class=\"container\"\n" +
                "        style=\"\n" +
                "          display: flex;\n" +
                "          flex-direction: column;\n" +
                "          justify-content: center;\n" +
                "          align-items: center;\n" +
                "          width: 800px;\n" +
                "          height: 600px;\n" +
                "          /* background-color: #ffc1cc; */\n" +
                "          border-radius: 20px;\n" +
                "          border: 3px solid #ffc1cc;\n" +
                "        \"\n" +
                "      >\n" +
                "        <img\n" +
                "          class=\"logoimage\"\n" +
                "          style=\"width: 120px; height: 80px\"\n" +
                "          src=\""+imageSrc+"\"\n" +
                "          alt=\"사진없음\"\n" +
                "        />\n" +
                "        <div\n" +
                "          class=\"content\"\n" +
                "          style=\"\n" +
                "            display: flex;\n" +
                "            flex-direction: column;\n" +
                "            justify-content: center;\n" +
                "            /* background-color: #ffc1cc; */\n" +
                "            align-items: center;\n" +
                "            padding: 20px;\n" +
                "            border-radius: 20px;\n" +
                "            border: 3px solid #ffc1cc;\n" +
                "            width: 500px;\n" +
                "          \"\n" +
                "        >\n" +
                "          <h1 class=\"font\">옷빌리지 보안 인증 메일</h1>\n" +
                "          <div class=\"blank\" style=\"padding: 22px\"></div>\n" +
                "          <div class=\"box\">\n" +
                "            <h3 class=\"font\" style=\"color: #ff453b\">\n" +
                "              인증번호를 알려드립니다.\n" +
                "            </h3>\n" +
                "          </div>\n" +
                "          <div\n" +
                "            class=\"upperbox\"\n" +
                "            style=\"\n" +
                "              background-color: rgba(255, 216, 123, 0.523);\n" +
                "              padding: 20px;\n" +
                "              border: 2px solid #ff8a5c;\n" +
                "              border-radius: 20px;\n" +
                "            \"\n" +
                "          >\n" +
                "            <div class=\"box\">\n" +
                "              <span class=\"font\" style=\"color: #ff453b\"\n" +
                "                >인증번호는\n" +
                "                <span style=\"font-size: 18px; font-weight: bold\"\n" +
                "                  >[" + randomNumber + "]</span\n" +
                "                >\n" +
                "                입니다.</span\n" +
                "              >\n" +
                "            </div>\n" +
                "          </div>\n" +
                "          <div class=\"blank2\" style=\"padding: 13px\"></div>\n" +
                "          <div class=\"box\">\n" +
                "            <span class=\"font\" style=\"color: #ff453b\"\n" +
                "              >원활한 서비스 이용을 위해 인증번호를 입력해주세요.</span\n" +
                "            >\n" +
                "          </div>\n" +
                "          <div class=\"box\">\n" +
                "            <span class=\"font\" style=\"color: #ff453b\"\n" +
                "              >인증번호를 확인 후 홈페이지에 입력해 주세요.</span\n" +
                "            >\n" +
                "          </div>\n" +
                "          <div class=\"box\">\n" +
                "            <span class=\"font\" style=\"color: #ff453b\"\n" +
                "              >옷빌리지를 이용해 주셔서 감사합니다.</span\n" +
                "            >\n" +
                "          </div>\n" +
                "        </div>\n" +
                "        <div class=\"blank3\" style=\"padding: 40px;\"></div>\n" +
                "      </div>\n" +
                "    </div>";

        message.setFrom("옷빌리지 <wearvillage.com@gmail.com>");
        message.setTo(mailDto.getAddress());
        message.setSubject("옷빌리지 인증번호 입니다.");
        message.setText(htmlContent, true);

        javaMailSender.send(mimeMessage);

        session.setAttribute("authCode", randomNumber);

        return randomNumber;
    }

    public int sendPwMail(GmailDto mailDto, HttpSession session) throws MessagingException {
        Random randomText = new Random();
        int randomNumber = randomText.nextInt((999999 - 100000) + 1) + 100000;
        String imageSrc = "http://localhost:8090/img/img_wearVillageSmallLogo.png";


        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "utf-8");

        String htmlContent = "    <div class=\"grid\" style=\"display: grid; justify-content: center\">\n" +
                "      <div\n" +
                "        class=\"container\"\n" +
                "        style=\"\n" +
                "          display: flex;\n" +
                "          flex-direction: column;\n" +
                "          justify-content: center;\n" +
                "          align-items: center;\n" +
                "          width: 800px;\n" +
                "          height: 600px;\n" +
                "          /* background-color: #ffc1cc; */\n" +
                "          border-radius: 20px;\n" +
                "          border: 3px solid #ffc1cc;\n" +
                "        \"\n" +
                "      >\n" +
                "        <img\n" +
                "          class=\"logoimage\"\n" +
                "          style=\"width: 120px; height: 80px\"\n" +
                "          src=\""+imageSrc+"\"\n" +
                "          alt=\"사진없음\"\n" +
                "        />\n" +
                "        <div\n" +
                "          class=\"content\"\n" +
                "          style=\"\n" +
                "            display: flex;\n" +
                "            flex-direction: column;\n" +
                "            justify-content: center;\n" +
                "            /* background-color: #ffc1cc; */\n" +
                "            align-items: center;\n" +
                "            padding: 20px;\n" +
                "            border-radius: 20px;\n" +
                "            border: 3px solid #ffc1cc;\n" +
                "            width: 500px;\n" +
                "          \"\n" +
                "        >\n" +
                "          <h1 class=\"font\">옷빌리지 임시 비밀번호</h1>\n" +
                "          <div class=\"blank\" style=\"padding: 22px\"></div>\n" +
                "          <div class=\"box\">\n" +
                "            <h3 class=\"font\" style=\"color: #ff453b\">\n" +
                "              임시비밀번호를 알려드립니다.\n" +
                "            </h3>\n" +
                "          </div>\n" +
                "          <div\n" +
                "            class=\"upperbox\"\n" +
                "            style=\"\n" +
                "              background-color: rgba(255, 216, 123, 0.523);\n" +
                "              padding: 20px;\n" +
                "              border: 2px solid #ff8a5c;\n" +
                "              border-radius: 20px;\n" +
                "            \"\n" +
                "          >\n" +
                "            <div class=\"box\">\n" +
                "              <span class=\"font\" style=\"color: #ff453b\"\n" +
                "                >임시비밀번호는\n" +
                "                <span style=\"font-size: 18px; font-weight: bold\"\n" +
                "                  >[" + randomNumber + "]</span\n" +
                "                >\n" +
                "                입니다.</span\n" +
                "              >\n" +
                "            </div>\n" +
                "          </div>\n" +
                "          <div class=\"blank2\" style=\"padding: 13px\"></div>\n" +
                "          <div class=\"box\">\n" +
                "            <span class=\"font\" style=\"color: #ff453b\"\n" +
                "              >고객님을 위한 임시 비밀번호를 발급해드렸습니다.</span\n" +
                "            >\n" +
                "          </div>\n" +
                "          <div class=\"box\">\n" +
                "            <span class=\"font\" style=\"color: #ff453b\"\n" +
                "              >임시비밀번호를 확인 후 홈페이지에 로그인해 주세요.</span\n" +
                "            >\n" +
                "          </div>\n" +
                "          <div class=\"box\">\n" +
                "            <span class=\"font\" style=\"color: #ff453b\"\n" +
                "              >옷빌리지를 이용해 주셔서 감사합니다.</span\n" +
                "            >\n" +
                "          </div>\n" +
                "        </div>\n" +
                "        <div class=\"blank3\" style=\"padding: 40px;\"></div>\n" +
                "      </div>\n" +
                "    </div>";

        message.setFrom("옷빌리지 <wearvillage.com@gmail.com>");
        message.setTo(mailDto.getAddress());
        message.setSubject("옷빌리지 임시 비밀번호 발급 메일");
        message.setText(htmlContent, true);

        javaMailSender.send(mimeMessage);

        session.setAttribute("authCode", randomNumber);

        return randomNumber;
    }

}