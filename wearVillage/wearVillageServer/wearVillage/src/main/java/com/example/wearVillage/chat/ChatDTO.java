package com.example.wearVillage.chat;

import lombok.*;


import java.sql.Timestamp;

@Getter
@Builder
@NoArgsConstructor
@Setter
@AllArgsConstructor
public class ChatDTO {
    private Integer CHAT_NUM;
    private String SENDER;
    private String ADDRESSEE;
    private String MESSAGE;
    private Integer CHATROOM;
    private Timestamp CHAT_DATE;
    private Integer CHAT_ROOM_ID;
    private String CHAT_MIME;

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
