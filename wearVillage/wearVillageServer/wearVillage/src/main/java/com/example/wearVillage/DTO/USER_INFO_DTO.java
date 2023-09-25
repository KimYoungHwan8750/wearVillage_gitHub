package com.example.wearVillage.DTO;

import com.example.wearVillage.Entity.USER_INFO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class USER_INFO_DTO {
    String ID;
    String PW;
    String NICKNAME;
    String EMAIL;
    String PROFILEIMG;
    String THEMA;
    String GENDER;
    String BIRTH;

//    public USER_INFO_DTO(USER_INFO userInfo){
//        this.ID=userInfo.getID();
//        this.PW=userInfo.getPW();
//        this.NICKNAME=userInfo.getNICKNAME();
//        this.EMAIL=userInfo.getEMAIL();
//        this.PROFILEIMG=userInfo.getPROFILEIMG();
//        this.THEMA=userInfo.getTHEMA();
//        this.SEX=userInfo.getSEX();
//        this.BIRTH=userInfo.getBIRTH();
//    }
}
