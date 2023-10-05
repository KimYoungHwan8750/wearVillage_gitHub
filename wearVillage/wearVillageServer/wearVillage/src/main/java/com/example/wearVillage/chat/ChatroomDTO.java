package com.example.wearVillage.chat;

import lombok.*;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatroomDTO {
    private int POST_ID;
    private String MEMBER1;
    private String MEMBER2;
    private String RECENTLY_MSG;
}
