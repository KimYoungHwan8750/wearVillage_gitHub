package com.example.wearVillage.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oracle.sql.NUMBER;
import oracle.sql.TIMESTAMP;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO {
    private int CHAT_NUM;
    private String SENDER;
    private String ADDRESSEE;
    private String MESSAGE;
    private int CHATROOM;
    private TIMESTAMP CHAT_DATE;
}
