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
        String payload = message.getPayload();
        JSONObject jsonPayload = new JSONObject(payload);
        String sender = (String) jsonPayload.get("sender");
        String addressee = (String) jsonPayload.get("addressee");
        String chatMessage = (String) jsonPayload.get("message");
        String chatroom = (String) jsonPayload.get("chatroom");
        System.out.println(jsonPayload.toString());






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