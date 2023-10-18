package com.example.wearVillage.DAO.myPagePWChangeDAO;

public interface myPagePWChangeDAO {
    int changePW(myPagePWChangeDTO dto);
    String getPWbyID(String ID);
}
