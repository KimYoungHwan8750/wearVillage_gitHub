package com.example.wearVillage.DTO;

import com.example.wearVillage.Entity.USER_INFO;

public class USER_INFO_DTO {
    private String ID;
    private String PW;
    private String NICKNAME;
    private String EMAIL;
    private String PROFILEIMG;
    private String THEMA;
    private String SEX;
    private String BIRTH;

    public USER_INFO_DTO(USER_INFO userInfo){
        this.ID=userInfo.getID();
        this.PW=userInfo.getPW();
        this.NICKNAME=userInfo.getNICKNAME();
        this.EMAIL=userInfo.getEMAIL();
        this.PROFILEIMG=userInfo.getPROFILEIMG();
        this.THEMA=userInfo.getTHEMA();
        this.SEX=userInfo.getSEX();
        this.BIRTH=userInfo.getBIRTH();
    }
}
