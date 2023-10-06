package com.example.wearVillage.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatroomDTO {
    @JsonProperty(value = "POST_ID")
    private int POST_ID;
    @JsonProperty(value = "MEMBER1")
    private String MEMBER1;
    @JsonProperty(value = "MEMBER2")
    private String MEMBER2;
    private String RECENTLY_MSG;
    private Timestamp RECENTLY_TIME;
}