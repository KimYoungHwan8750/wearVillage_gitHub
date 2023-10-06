package com.example.wearVillage.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ChatServiceImpl implements ChatService{
    private final ChatroomDAO chatroomdao;
    private final ChatDAOJpaImpl chatDaoJpaImpl;
    private final ChatDAO chatDao;
    @Override
    public boolean isThereChatroom(String sender,String addressee, int chatroom){
        //채팅방 없으면 생성
        if(!chatroomdao.isThereSenderAndChatroom(sender,chatroom)){
        chatroomdao.createChatroom(sender,addressee,chatroom);
        //채팅방이 만들어지므로 true반환
        return true;
        } else {
        return false;
        }
    }

    @Override
    public void saveChat(ChatEntity chatEntity){
        chatDaoJpaImpl.save(chatEntity);
    }

    @Override
    public Integer maxNumUserChat(){
        return chatDao.maxNumUserChat();
    }
    @Override
    public boolean createChatRoom(String sender, String addressee, int chatroom){
        return chatroomdao.createChatroom(sender,addressee,chatroom);
    }

    @Override
    public List<ChatDTO> loadingChatHistory(int chatroom, String member1, String member2){
        return chatroomdao.loadingChatHistory(chatroom,member1,member2);
    }

}
