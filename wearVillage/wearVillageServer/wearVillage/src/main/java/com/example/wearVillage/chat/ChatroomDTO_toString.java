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
public class ChatroomDTO_toString {
    @JsonProperty(value = "POST_ID")
    private int POST_ID;
    @JsonProperty(value = "MEMBER1")
    private String MEMBER1;
    @JsonProperty(value = "MEMBER2")
    private String MEMBER2;
    private String RECENTLY_MSG;
    private String RECENTLY_TIME;
    private Integer CHAT_ROOM_ID;
    private String POST_WRITER_ID;
    private String POST_SUBTITLE;
    private String POST_PRICE;
    private String POST_RENT_DEFAULT_PRICE;
    private String POST_RENT_DAY_PRICE;
    private String POST_MAP_INFO;
    private String POST_THUMBNAIL_IMG;
}
