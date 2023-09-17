package com.example.wearVillage.DAO;

import com.example.wearVillage.DTO.userChatDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class userChatDAO {

    private final JdbcTemplate jdbcTemplate;
    @Autowired


    userChatDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }



    public void userChat_to_oracle(userChatDTO userChatDTO){
        String sql = "SELECT * FROM USER_CHAT WHERE";
        jdbcTemplate.update(sql);

    }

    public void oracle_to_userChat(){

    }
}

