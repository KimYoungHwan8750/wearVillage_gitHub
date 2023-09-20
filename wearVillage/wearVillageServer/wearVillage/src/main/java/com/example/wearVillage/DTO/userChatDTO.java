package com.example.wearVillage.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Data
public class userChatDTO {
    private String user_id;
    private String target_id;
    private String message;
    private String chat_date;
}
