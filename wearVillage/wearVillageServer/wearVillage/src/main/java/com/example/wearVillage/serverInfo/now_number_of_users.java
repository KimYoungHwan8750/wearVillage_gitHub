package com.example.wearVillage.serverInfo;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class now_number_of_users implements HttpSessionListener {

    private final AtomicInteger activeSessions;

    public now_number_of_users() {
        super();
        activeSessions = new AtomicInteger();
    }

    public int getTotalActiveSession(){
        return activeSessions.get();
    }

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        activeSessions.incrementAndGet();
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        activeSessions.decrementAndGet();
    }
}