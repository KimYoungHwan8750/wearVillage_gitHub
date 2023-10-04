package com.example.wearVillage.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oracle.sql.TIMESTAMP;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatEntity {
    private int CHAT_NUM;
    private String SENDER;
    private String ADDRESSEE;
    private String MESSAGE;
    private int CHATROOM;
    private TIMESTAMP CHAT_DATE;

    ChatEntity(ChatDTO chatdto){
        this.SENDER = chatdto.getSENDER();
        this.CHAT_NUM = chatdto.getCHAT_NUM();
        this.CHATROOM = chatdto.getCHATROOM();
        this.ADDRESSEE = chatdto.getADDRESSEE();
        this.CHAT_DATE = chatdto.getCHAT_DATE();
        this.MESSAGE= chatdto.getMESSAGE();
    }
}