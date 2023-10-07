package com.example.wearVillage.Handler;


import com.example.wearVillage.chat.ChatDTO;
import com.example.wearVillage.chat.ChatEntity;
import com.example.wearVillage.chat.ChatService;
import com.example.wearVillage.chat.ChatroomEntity;
import lombok.RequiredArgsConstructor;
import oracle.sql.TIMESTAMP;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.sql.Timestamp;
import java.util.concurrent.ConcurrentLinkedQueue;

import static java.lang.Integer.parseInt;

//@Slf4j
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final JdbcTemplate jdbcTemplate;
    private final ChatService chatSVC;
    private static final Logger log = LoggerFactory.getLogger(WebSocketHandler.class);
    private static final ConcurrentLinkedQueue<WebSocketSession> sessions= new ConcurrentLinkedQueue<>();

    public WebSocketHandler(JdbcTemplate jdbcTemplate, ChatService chatSVC) {
        this.jdbcTemplate = jdbcTemplate;
        this.chatSVC = chatSVC;
    }

    // 메세지 처리하는 메소드
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        JSONObject jsonPayload = new JSONObject(payload);
        String sender = jsonPayload.getString("sender");
        String addressee = jsonPayload.getString("addressee");
        String chatMessage = jsonPayload.getString("message");
        int chatroom = jsonPayload.getInt("chatroom");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        ChatDTO chatDto = ChatDTO.builder()
                .CHAT_NUM(chatSVC.maxNumUserChat())
                .SENDER(sender)
                .ADDRESSEE(addressee)
                .MESSAGE(chatMessage)
                .CHATROOM(chatroom)
                .CHAT_DATE(null)
                .CHAT_ROOM_ID(chatSVC.searchChatroom(sender,addressee,chatroom).getCHAT_ROOM_ID())
                .build();
        ChatEntity chatEntity = new ChatEntity(chatDto);
        chatSVC.saveChat(chatEntity);






        for(WebSocketSession sess: sessions) {
            sess.sendMessage(message);
        }
    }


    // client 접속 시 호출되는 메서드
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println(session + "클라이언트 접속");

        sessions.add(session);
        log.info(session + " 클라이언트 접속");
    }

    // client 접속 해제 시 호출되는 메서드드
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.info(session + " 클라이언트 접속 해제");
        sessions.remove(session);

    }
}