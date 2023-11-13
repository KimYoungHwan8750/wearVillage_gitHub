package com.example.wearVillage.DAO;

import com.example.wearVillage.Entity.AskObject;
import com.example.wearVillage.Entity.FaqObject;
import lombok.Data;

import java.util.List;

public interface FaqPostDAO {

    // 전체 게시글 조회 -- 자주묻는질문 목록 리스트
    List<FaqObject> faqFindAll();

    // 개별 게시글 조회 -- faqpostid 인풋
    FaqObject faqFind(String faqpostid);

}
