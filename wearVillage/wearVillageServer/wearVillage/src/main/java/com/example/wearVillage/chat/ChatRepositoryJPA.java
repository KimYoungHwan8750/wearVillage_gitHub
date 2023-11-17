package com.example.wearVillage.chat;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

@Repository
@RequiredArgsConstructor
public class ChatRepositoryJPA {
    private final JdbcTemplate jdbcTemplate;

    public ChatEntity findChatEntityByChatNum(int chatNum){
        String sql = "SELECT * FROM USER_CHAT WHERE CHAT_NUM = ?";

            ChatEntity chatEntity = jdbcTemplate.queryForObject(sql, new RowMapper<ChatEntity>() {
                @Override
                public ChatEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                    ChatEntity entity = new ChatEntity();
                    entity.setCHAT_NUM(rs.getInt("CHAT_NUM"));
                    entity.setCHAT_ROOM_ID(rs.getInt("CHAT_ROOM_ID"));
                    entity.setCHAT_MIME(rs.getString("CHAT_MIME"));
                    entity.setCHAT_DATE(rs.getTimestamp("CHAT_DATE"));
                    entity.setCHATROOM(rs.getInt("POST_ID"));
                    entity.setADDRESSEE(rs.getString("ADDRESSEE"));
                    entity.setMESSAGE(rs.getString("MESSAGE"));
                    entity.setSENDER(rs.getString("SENDER"));
                    return entity;
                }
            }, chatNum);




        return chatEntity;
    }

    public void save(ChatEntity chatEntity){
        String sql = "INSERT INTO USER_CHAT VALUES(?,?,?,?,?,?,?,?)";
        Map<String,Object> map = new HashMap<>();
        Object[] obj = new Object[]{
                chatEntity.getCHAT_NUM(),
                chatEntity.getSENDER(),
                chatEntity.getADDRESSEE(),
                chatEntity.getMESSAGE(),
                chatEntity.getCHATROOM(),
                chatEntity.getCHAT_DATE(),
                chatEntity.getCHAT_ROOM_ID(),
                chatEntity.getCHAT_MIME()
        };
        int update = jdbcTemplate.update(sql,obj);
    }
}
