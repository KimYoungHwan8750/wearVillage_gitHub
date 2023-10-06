package com.example.wearVillage.chat;

import lombok.RequiredArgsConstructor;

import java.util.List;

public interface ChatService {
    //채팅 입력했을 때 채팅방 있는지 체크하고 없으면 채팅방 개설
    boolean isThereChatroom(String sender, String addressee,int chatroom);
    //채팅 내역 저장
    void saveChat(ChatEntity chatEntity);
    //채팅 내역을 저장할 때 채팅번호중 가장 큰 값을 구해 +1. 시퀀스를 구현했다고 생각하면 됨.
    Integer maxNumUserChat();
    //채팅방 만들기. 채팅방 만들면 true반환
    boolean createChatRoom(String sender, String addressee, int chatroom);
    List<ChatDTO> loadingChatHistory(int chatroom, String member1, String member2);
    ChatroomDTO searchChatroom(String sender, String addressee, int chatroom);
    List<ChatroomDTO> loadingChatroom(String nickname);
}
