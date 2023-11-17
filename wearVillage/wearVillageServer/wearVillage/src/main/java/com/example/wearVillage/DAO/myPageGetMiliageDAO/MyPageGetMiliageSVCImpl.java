package com.example.wearVillage.DAO.myPageGetMiliageDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyPageGetMiliageSVCImpl implements MyPageGetMiliageSVC{

    private final MyPageGetMiliageDAO myPageGetMiliageDAO;
    @Override
    public int getMiliage(String userID, MyPageGetMiliageForm myPageGetMiliageForm) {
        return myPageGetMiliageDAO.getMiliage(userID,myPageGetMiliageForm);
    }

    @Override
    public int withDrawMiliage(String ID, int withDrawMiliage){

        return myPageGetMiliageDAO.withDrawMiliage(ID,withDrawMiliage);
    }
}
