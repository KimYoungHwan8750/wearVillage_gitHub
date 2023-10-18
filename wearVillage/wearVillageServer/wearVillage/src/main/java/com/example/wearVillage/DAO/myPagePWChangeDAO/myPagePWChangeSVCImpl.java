package com.example.wearVillage.DAO.myPagePWChangeDAO;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@Repository
@RequiredArgsConstructor
public class myPagePWChangeSVCImpl implements myPagePWChangeSVC{

    private final myPagePWChangeDAO dao;
    @Override
    public int changePW(String userId, ChangeUserPWForm form) {
        myPagePWChangeDTO dto = new myPagePWChangeDTO(userId,form);
        return dao.changePW(dto);
    }

}
