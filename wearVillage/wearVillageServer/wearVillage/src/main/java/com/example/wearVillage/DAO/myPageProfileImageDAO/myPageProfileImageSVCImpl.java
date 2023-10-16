package com.example.wearVillage.DAO.myPageProfileImageDAO;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@Repository
@RequiredArgsConstructor
public class myPageProfileImageSVCImpl implements myPageProfileImageSVC{

    private final myPageProfileImageDAO myPageProfileImageDAO;
    @Override
    public int changeProfileImage(String ID, String PROFILEIMAGE) {
        return myPageProfileImageDAO.changeProfileImage(ID,PROFILEIMAGE);
    }
}
