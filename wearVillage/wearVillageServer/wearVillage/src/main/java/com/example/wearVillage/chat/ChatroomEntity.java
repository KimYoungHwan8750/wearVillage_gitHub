package com.example.wearVillage.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@Setter
@Getter

public class ChatroomEntity {
    private int POST_ID;
    private String MEMBER1;
    private String MEMBER2;
    private String RECENTLY_MSG;
    private Timestamp RECENTLY_TIME;
    private Integer CHAT_ROOM_ID;
    ChatroomEntity(ChatroomDTO chatDto){
        this.POST_ID = chatDto.getPOST_ID();
        this.MEMBER1 = chatDto.getMEMBER1();
        this.MEMBER2 = chatDto.getMEMBER2();
        this.RECENTLY_MSG = chatDto.getRECENTLY_MSG();
        this.RECENTLY_TIME = chatDto.getRECENTLY_TIME();
        this.CHAT_ROOM_ID = chatDto.getCHAT_ROOM_ID();
    }
}
