package com.example.wearVillage.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ChatServiceImpl implements ChatService{
    private final ChatroomDAO chatroomdao;
    private final ChatDAOJpaImpl chatDaoJpaImpl;
    private final ChatDAOImpl chatDaoImpl;
    @Override
    public void isThereChatroom(String sender,String addressee, int chatroom){
        //채팅방 없으면 생성
        if(!chatroomdao.isThereSenderAndChatroom(sender,chatroom)){
        chatroomdao.createChatroom(sender,addressee,chatroom);
        }
    }

    @Override
    public void saveChat(ChatDTO chatDTO){
        ChatEntity chatEntity = new ChatEntity(chatDTO);
        chatDaoJpaImpl.save(chatEntity);
    }

    @Override
    public Integer maxNumUserChat(){
        return chatDaoImpl.maxNumUserChat();
    }
}
