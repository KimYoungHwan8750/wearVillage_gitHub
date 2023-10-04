package com.example.wearVillage.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public class ChatroomDAOImpl implements ChatroomDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    ChatroomDAOImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Boolean isThereSenderAndChatroom(String sender, int chatroom){
        String sql = "SELECT * FROM CHAT_ROOM WHERE SENDER = ? AND CHAT_ROOM = ?";
//        Optional<ChatroomDTO> chatroomdto = jdbcTemplate.queryForObject(sql, Optional.class);
//        if(Optional.empty().isEmpty()){
//
//        }
        return false;
    }

}
