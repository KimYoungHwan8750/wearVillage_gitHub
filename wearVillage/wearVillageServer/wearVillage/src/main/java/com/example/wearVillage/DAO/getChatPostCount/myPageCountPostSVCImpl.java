package com.example.wearVillage.DAO.getChatPostCount;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class myPageCountPostSVCImpl implements myPageCountPostSVC{

    private final myPageCountPostDAO myPageCountPostDAO;
    @Override
    public int countPost(String ID, String postWriterId) {
        return myPageCountPostDAO.countPost(ID,postWriterId);
    }
}
