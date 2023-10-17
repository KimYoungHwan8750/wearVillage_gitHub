package com.example.wearVillage.DAO.myPageProfileImageDAO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class USER_INFO_FORM {
        private String ID;
        private String PROFILEIMG;
}
