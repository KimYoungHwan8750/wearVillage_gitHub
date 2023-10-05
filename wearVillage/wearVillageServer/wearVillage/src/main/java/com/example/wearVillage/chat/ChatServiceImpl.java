package com.example.wearVillage.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ChatServiceImpl implements ChatService{
    private final ChatroomDAO chatroomdao;
    @Override
    public void isThereChatroom(String sender,String addressee, int chatroom){
        //채팅방 없으면 생성
        if(!chatroomdao.isThereSenderAndChatroom(sender,chatroom)){
        chatroomdao.createChatroom(sender,addressee,chatroom);
        }
    }

}
