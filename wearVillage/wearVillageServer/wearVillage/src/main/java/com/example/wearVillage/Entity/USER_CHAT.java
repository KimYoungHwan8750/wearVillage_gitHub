package com.example.wearVillage.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class USER_CHAT {
    @Id
    @Column(name="CHAT_NUM")
    private String CHAT_NUM;
    @Column(name="MESSAGE")
    private String MESSAGE;
    @Column(name="CHAT_DATE")
    private String CHAT_DATE;
    @Column(name="SENDER")
    private String SENDER;

    @Column(name="ADDRESSEE")
    private String ADDRESSEE;
    @Column(name="CHATROOM")
    private String CHATROOM;

}
