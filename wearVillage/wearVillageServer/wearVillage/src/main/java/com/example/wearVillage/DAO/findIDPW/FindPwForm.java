package com.example.wearVillage.DAO.findIDPW;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class FindPwForm {
    private String id;
    @Email(message = "이메일 형식을 맞춰주세요")
    private String email;
}
