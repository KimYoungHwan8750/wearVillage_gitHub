package com.example.wearVillage.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.sql.Timestamp;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO {
    private Integer CHAT_NUM;
    private String SENDER;
    private String ADDRESSEE;
    private String MESSAGE;
    private Integer CHATROOM;
    private Timestamp CHAT_DATE;

    @Override
    public String toString(){
        return CHAT_NUM+":"+
                SENDER+":"+
                ADDRESSEE+":"+
                MESSAGE+":"+
                CHATROOM+":"+
                CHAT_DATE;
    }
}
