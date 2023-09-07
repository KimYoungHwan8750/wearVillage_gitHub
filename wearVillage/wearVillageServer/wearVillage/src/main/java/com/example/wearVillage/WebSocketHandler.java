package com.example.wearVillage;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private List<WebSocketSession> sessionList = new ArrayList<>();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        String name = session.getHandshakeHeaders().get("name").get(0);
        sessionList.add(session);
        System.out.println("sessionList = " + sessionList.size());
        sessionList.forEach(s-> {
            try {
                s.sendMessage(new TextMessage(name+"님께서 입장하셨습니다."));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        Gson gson = new Gson();
        String name = session.getHandshakeHeaders().get("name").get(0);
        sessionList.forEach(s-> {
            try {
                ChatMessage chatMessage = gson.fromJson(message.getPayload(), ChatMessage.class);
                s.sendMessage(new TextMessage(name + " : "+chatMessage.getContent()+"["+chatMessage.getTime()+"]"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        sessionList.remove(session);
        System.out.println("session = " + sessionList.size());
        String name = session.getHandshakeHeaders().get("name").get(0);
        sessionList.forEach(s-> {
            try {
                s.sendMessage(new TextMessage(name+"님께서 퇴장하셨습니다."));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}