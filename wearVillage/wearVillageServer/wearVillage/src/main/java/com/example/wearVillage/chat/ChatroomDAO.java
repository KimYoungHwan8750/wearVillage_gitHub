package com.example.wearVillage.chat;

import com.example.wearVillage.Entity.CHAT_ROOM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;


public interface ChatroomDAO {
    Boolean isThereSenderAndChatroom(String sender, int chatroom);
}
