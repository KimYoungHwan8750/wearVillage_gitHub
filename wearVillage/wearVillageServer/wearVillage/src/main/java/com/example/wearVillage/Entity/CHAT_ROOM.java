package com.example.wearVillage.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.sql.Timestamp;

@Entity
public class CHAT_ROOM {
    @Id
    @GeneratedValue()
    @Column(name = "ROOM_ID")
    private int roomId;
    @Column(name = "ID")

    private String id;
    @Column(name = "TARGET_ID")

    private String targetId;
    @Column(name = "RENEWAL_DATE")

    private Timestamp renewalDate;

}
