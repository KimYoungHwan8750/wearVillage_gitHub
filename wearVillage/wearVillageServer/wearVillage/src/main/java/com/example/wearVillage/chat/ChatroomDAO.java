package com.example.wearVillage.chat;


public interface ChatroomDAO {
    Boolean isThereSenderAndChatroom(String sender,int chatroom);
    void createChatroom(String sender, String addressee, int chatroom);
}
