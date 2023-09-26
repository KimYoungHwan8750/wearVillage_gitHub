package com.example.wearVillage.Handler;


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

import java.util.concurrent.ConcurrentLinkedQueue;

//@Slf4j
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final JdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(WebSocketHandler.class);
    private static final ConcurrentLinkedQueue<WebSocketSession> sessions= new ConcurrentLinkedQueue<>();

    public WebSocketHandler(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 메세지 처리하는 메소드
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        System.out.println("메세지:" + message);
        String payload = message.getPayload();
        JSONObject jsonPayload = new JSONObject(payload);
        System.out.println("메세지: "+payload);
        String[] chat_formData = payload.split("'wearCutLines'");
//        System.out.println("챗오라클확인1");
//        for (int i = 0; i < chat_formData.length; i++) {
//            System.out.println(chat_formData[i]);
//            System.out.println(i+"번째 검색");
//
//        }

        String user_id = chat_formData[0];
        String target_id = chat_formData[1];
        String chat_message = chat_formData[2];
        String chat_typing_time = chat_formData[4];
        String chatPlace_history = chat_formData[5];
        jdbcTemplate.update("INSERT INTO USER_CHAT(CHAT_NUM,USER_ID,TARGET_ID,MESSAGE,CHATROOM_ID,CHAT_DATE) VALUES (CHAT_NUM_COUNT.NEXTVAL,?,?,?,?,?)",user_id,target_id,chat_message,chatPlace_history,chat_typing_time);

//        myId:arr[0],
//                target_id:arr[1],
//                message:arr[2],
//                chat_typing_time:arr[4],
//                chatPlace_history:arr[5]
//        log.info("payload : " + payload);

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
        System.out.println(session + "클라이언트 접속 해제" + status);
        log.info(session + " 클라이언트 접속 해제" + status);
        sessions.remove(session);

    }
}