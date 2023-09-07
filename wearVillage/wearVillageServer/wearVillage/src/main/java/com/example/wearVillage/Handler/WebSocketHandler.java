package com.example.wearVillage.Handler;

import groovy.transform.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private static final ConcurrentLinkedQueue<WebSocketSession> sessions= new ConcurrentLinkedQueue<>();

    // 메세지 처리하는 메소드
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("확인3");
        String payload = message.getPayload();
        System.out.println(payload);
        log.info("payload : " + payload);

        for(WebSocketSession sess: sessions) {
            sess.sendMessage(message);
        }
    }


    // client 접속 시 호출되는 메서드
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
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