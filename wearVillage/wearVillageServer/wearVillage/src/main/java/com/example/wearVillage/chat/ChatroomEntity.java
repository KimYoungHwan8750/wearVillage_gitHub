package com.example.wearVillage.chat;

import lombok.NoArgsConstructor;

@NoArgsConstructor

public class ChatroomEntity {
    private int POST_ID;
    private String MEMBER1;
    private String MEMBER2;
    private String RECENTLY_MSG;
    ChatroomEntity(ChatroomDTO chatDto){
        this.POST_ID = chatDto.getPOST_ID();
        this.MEMBER1 = chatDto.getMEMBER1();
        this.MEMBER2 = chatDto.getMEMBER2();
        this.RECENTLY_MSG = chatDto.getRECENTLY_MSG();
    }
}
