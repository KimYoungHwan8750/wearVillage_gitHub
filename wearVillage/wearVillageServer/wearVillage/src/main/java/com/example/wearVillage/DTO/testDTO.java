package com.example.wearVillage.DTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class testDTO {



    public static void main(String[] args) {
        String testt = "11/22/33/44";
        String[] chat_formdata= testt.split("/");
        System.out.println(chat_formdata[1]);
    }
}
