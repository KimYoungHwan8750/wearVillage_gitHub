package com.example.wearVillage.DAO.myPageGetMiliageDAO;

public interface MyPageGetMiliageSVC {
    int getMiliage(String userID,MyPageGetMiliageForm myPageGetMiliageForm);
    int withDrawMiliage(String ID, int withDrawMiliage);
}
