package com.example.wearVillage.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class USER_INFO {
    @Id
    private String ID;
    private String PW;
    private String NICKNAME;
    private String EMAIL;
    private String PROFILEIMG;
    private String THEMA;
    private String SEX;
    private String BIRTH;
}
