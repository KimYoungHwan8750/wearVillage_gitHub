package com.example.wearVillage.chat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oracle.sql.TIMESTAMP;

import java.sql.Timestamp;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
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
    @Column(name="CHATROOM")
    private Integer CHATROOM;
    @Column(name="CHAT_DATE")
    private Timestamp CHAT_DATE;

    ChatEntity(ChatDTO chatdto){
        this.SENDER = chatdto.getSENDER();
        this.CHAT_NUM = chatdto.getCHAT_NUM();
        this.CHATROOM = chatdto.getCHATROOM();
        this.ADDRESSEE = chatdto.getADDRESSEE();
        this.CHAT_DATE = chatdto.getCHAT_DATE();
        this.MESSAGE= chatdto.getMESSAGE();
    }
}