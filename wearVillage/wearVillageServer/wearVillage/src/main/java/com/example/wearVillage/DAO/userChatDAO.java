package com.example.wearVillage.DAO;

<<<<<<< HEAD
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
=======
import com.example.wearVillage.DTO.userChatDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

>>>>>>> 3ea686e29dc9b060e6ae9b93e927c07d70c4c89f
@Repository
public class userChatDAO {

    private final JdbcTemplate jdbcTemplate;
    @Autowired


    userChatDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }



    public void userChat_to_oracle(){
        String sql = "SELECT * FROM USER_CHAT WHERE";
        jdbcTemplate.update(sql);

    }

    public List<Map<String,Object>> oracle_to_userChat(String user_id, String chatroom_id){
                return jdbcTemplate.queryForList(
                        "SELECT * FROM USER_CHAT WHERE USER_ID = ? OR TARGET_ID = ? AND CHATROOM_ID = ? ORDER BY CHAT_NUM ASC",user_id,user_id,chatroom_id);
    }
}