package com.example.wearVillage.DAO.myPageGetMyPostCountsDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class myPageGetPostCountSVCImpl implements myPageGetPostCountSVC{

    private final myPageGetMyPostCountsDAO dao;

    @Override
    public int findMyCounts(String ID) {
        return dao.findMyCounts(ID);
    }
}
