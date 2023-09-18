package com.example.wearVillage.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Component
@Setter
public class UserInfoDTO {
    private String user_id;
    private String user_email;
}
