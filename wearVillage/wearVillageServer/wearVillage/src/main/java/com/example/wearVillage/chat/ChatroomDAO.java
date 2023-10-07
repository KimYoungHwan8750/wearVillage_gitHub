package com.example.wearVillage.chat;


import java.util.List;

public interface ChatroomDAO {
    // 채팅방이 있으면 true 없으면 false 반환
    Boolean isThereSenderAndChatroom(String sender,int chatroom);
    // 채팅방 생성
    boolean createChatroom(String sender, String addressee, int chatroom);
    // 채팅방을 통해 채팅내역 불러오기
    List<ChatDTO> loadingChatHistory(int chatroom, String member1, String member2);
    List<ChatroomDTO> loadingChatroomList(String nickname);

    Integer maxNumChatroomId();
    ChatroomDTO searchChatroom(String sender,String addressee, int chatroom);
}
