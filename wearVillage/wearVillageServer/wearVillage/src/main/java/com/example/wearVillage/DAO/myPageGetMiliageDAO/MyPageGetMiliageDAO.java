package com.example.wearVillage.DAO.myPageGetMiliageDAO;

public interface MyPageGetMiliageDAO {
    int getMiliage(String userID,MyPageGetMiliageForm myPageGetMiliageForm);
    int withDrawMiliage(String ID,int drawMiliage);
}
