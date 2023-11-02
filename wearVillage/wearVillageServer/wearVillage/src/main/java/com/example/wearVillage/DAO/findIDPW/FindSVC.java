package com.example.wearVillage.DAO.findIDPW;

public interface FindSVC {
    String findId(String email);
    String findPwByIdAndPw(String id, String email);

    void setTempPw(String email, String id, String pw);
}
