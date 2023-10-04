package com.example.wearVillage.chat;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChatService {
    private final ChatroomDAO chatroomdao;
    private final ChatDAO chatdao;
    void test(String a, int b) {
        chatroomdao.isThereSenderAndChatroom(a,b);
    }

}
