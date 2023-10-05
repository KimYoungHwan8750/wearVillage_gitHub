package com.example.wearVillage.chat;

import lombok.RequiredArgsConstructor;

public interface ChatService {
    //채팅 입력했을 때 채팅방 있는지 체크하고 없으면 채팅방 개설
    void isThereChatroom(String sender, String addressee,int chatroom);
    void saveChat(ChatDTO chatDTO);
    Integer maxNumUserChat();

}
