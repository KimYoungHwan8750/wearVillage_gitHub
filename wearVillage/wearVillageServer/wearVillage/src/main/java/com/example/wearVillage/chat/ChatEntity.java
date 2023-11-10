package com.example.wearVillage.chat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import oracle.sql.TIMESTAMP;

import java.sql.Timestamp;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Table(name="USER_CHAT")
public class ChatEntity {
    @Id
    @Column(name="CHAT_NUM")
    private Integer CHAT_NUM;
    @Column(name="SENDER")
    private String SENDER;
    @Column(name="ADDRESSEE")
    private String ADDRESSEE;
    @Column(name="MESSAGE")
    private String MESSAGE;
    @Column(name="POST_ID")
    private Integer CHATROOM;
    @Column(name="CHAT_DATE")
    private Timestamp CHAT_DATE;
    @Column(name="CHAT_ROOM_ID")
    private Integer CHAT_ROOM_ID;
    @Column(name="CHAT_MIME")
    private String CHAT_MIME;

    public ChatEntity(ChatDTO chatdto){
        this.SENDER = chatdto.getSENDER();
        this.CHAT_NUM = chatdto.getCHAT_NUM();
        this.CHATROOM = chatdto.getCHATROOM();
        this.ADDRESSEE = chatdto.getADDRESSEE();
        this.CHAT_DATE = chatdto.getCHAT_DATE();
        this.MESSAGE= chatdto.getMESSAGE();
        this.CHAT_ROOM_ID = chatdto.getCHAT_ROOM_ID();
        this.CHAT_MIME = chatdto.getCHAT_MIME();
    }
}