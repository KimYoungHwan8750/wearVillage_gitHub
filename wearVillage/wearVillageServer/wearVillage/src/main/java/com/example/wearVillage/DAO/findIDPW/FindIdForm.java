package com.example.wearVillage.DAO.findIDPW;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class FindIdForm {
    @Email
    private String email;
}
