package com.example.wearVillage.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class USER_CHAT {
    @Id
    private String CHAT_NUM;
    private String MESSAGE;
    private String CHAT_DATE;
    private String USER_ID;
    private String TARGET_ID;
}
