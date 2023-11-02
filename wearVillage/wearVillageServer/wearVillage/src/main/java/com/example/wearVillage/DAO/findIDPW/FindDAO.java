package com.example.wearVillage.DAO.findIDPW;

public interface FindDAO {
    String findId(String email);

    String findPwByIdAndPw(String id, String email);

    void setTempPw(String email, String id, String pw);
}
