package com.example.wearVillage.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatDAOImpl implements ChatDAO {
    private final JdbcTemplate jdbcTemplate;


    @Override
    public Integer maxNumUserChat(){
        Integer result = jdbcTemplate.queryForObject("SELECT MAX(CHAT_NUM) FROM USER_CHAT",Integer.class);
        if (result==null){
            return 0;
        }
        return result+1;
    }
}
