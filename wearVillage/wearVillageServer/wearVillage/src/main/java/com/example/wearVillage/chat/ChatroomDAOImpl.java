package com.example.wearVillage.chat;

import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class ChatroomDAOImpl implements ChatroomDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    ChatroomDAOImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Boolean isThereSenderAndChatroom(String sender,int chatroom){
        try {
            String sql = "SELECT POST_ID, MEMBER1, MEMBER2, RECENTLY_MSG FROM CHAT_ROOM WHERE (MEMBER1 = ? OR MEMBER2 = ?) AND POST_ID = ?";
            ChatroomDTO chatroomdto = jdbcTemplate.queryForObject(sql, new RowMapper<ChatroomDTO>() {
                @Override
                public ChatroomDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                    ChatroomDTO chatroomDTO = new ChatroomDTO();
                    chatroomDTO.setPOST_ID(rs.getInt("POST_ID"));
                    chatroomDTO.setMEMBER1(rs.getString("MEMBER1"));
                    chatroomDTO.setMEMBER2(rs.getString("MEMBER2"));
                    chatroomDTO.setRECENTLY_MSG(rs.getString("RECENTLY_MSG"));

                    return chatroomDTO;
                }
            }, sender, sender, chatroom);
            return true;
        }catch (EmptyResultDataAccessException e){
            log.info("조회된 채팅방이 없습니다.");
            return false;
        }catch (IncorrectResultSizeDataAccessException e){
            log.info("이 메서드에서 여러개의 로우가 조회될 일은 없습니다. 이 에러가 발생한다면 코드를 검토해야합니다.");
            return false;
        }
    }


    @Override
    public boolean createChatroom(String sender, String addressee, int chatroom){
        String sql = "INSERT INTO CHAT_ROOM VALUES (?,?,?,?,SYSTIMESTAMP)";
        log.info("채팅방 생성 완료");
        //채팅방이 생성되면 1이 되면서 true반환 그외의 경우엔 false 반환
        return jdbcTemplate.update(sql,chatroom,sender,addressee," ")==1;

    }

    @Override
    public List<ChatDTO> loadingChatHistory(int chatroom, String member1, String member2){
        String sql =
                        "SELECT * FROM USER_CHAT " +
                        "WHERE (SENDER = ? OR ADDRESSEE = ?) " +
                        "AND (SENDER = ? OR ADDRESSEE = ?) " +
                        "AND POST_ID = ? " +
                        "ORDER BY CHAT_NUM ASC";
        List<ChatEntity> chatEntityList = jdbcTemplate.query(sql, new Object[]{member1, member1, member2, member2, chatroom}, new RowMapper<ChatEntity>() {
            @Override
            public ChatEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                ChatEntity chatEntity = new ChatEntity();
                chatEntity.setCHAT_NUM(rs.getInt("CHAT_NUM"));
                chatEntity.setCHATROOM(rs.getInt("POST_ID"));
                chatEntity.setCHAT_DATE(rs.getTimestamp("CHAT_DATE"));
                chatEntity.setSENDER(rs.getString("SENDER"));
                chatEntity.setADDRESSEE(rs.getString("ADDRESSEE"));
                chatEntity.setMESSAGE(rs.getString("MESSAGE"));
                return chatEntity;
            }
        });
        List<ChatDTO> chatdto = chatEntityList.stream()
                        .map(m-> new ChatDTO(m.getCHAT_NUM(),
                                             m.getSENDER(),
                                             m.getADDRESSEE(),
                                             m.getMESSAGE(),
                                             m.getCHATROOM(),
                                             m.getCHAT_DATE())).toList();
        System.out.println(chatdto.toString());
        return chatdto;
    }


}
