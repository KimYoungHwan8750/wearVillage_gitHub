package com.example.wearVillage.DAO;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
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

    public List<Map<String,Object>> oracle_to_userChat(String user_id, String target_id, String chatroom_id){
                return jdbcTemplate.queryForList(
                        "SELECT * FROM USER_CHAT WHERE USER_ID = ? AND TARGET_ID = ? AND CHATROOM_ID = ?",user_id,target_id,chatroom_id);
    }
}