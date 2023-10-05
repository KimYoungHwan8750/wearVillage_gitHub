package com.example.wearVillage.chat;

import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor

public class ChatroomEntity {
    private int POST_ID;
    private String MEMBER1;
    private String MEMBER2;
    private String RECENTLY_MSG;
    private Timestamp RECENTLY_TIME;
    ChatroomEntity(ChatroomDTO chatDto){
        this.POST_ID = chatDto.getPOST_ID();
        this.MEMBER1 = chatDto.getMEMBER1();
        this.MEMBER2 = chatDto.getMEMBER2();
        this.RECENTLY_MSG = chatDto.getRECENTLY_MSG();
        this.RECENTLY_TIME = chatDto.getRECENTLY_TIME();
    }
}
