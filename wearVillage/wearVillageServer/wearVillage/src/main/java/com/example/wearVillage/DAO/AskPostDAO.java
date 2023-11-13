package com.example.wearVillage.DAO;

import com.example.wearVillage.Entity.AskObject;

import java.util.List;

public interface AskPostDAO {

    // 게시글 쓰기
    String askWrite(AskObject askObject);
    //-- 게시글이 오면 성공 여부만 반환

    // 전체 게시글 조회
    List<AskObject> askFindAll(String sid);
    //-- INPUT 없이 요청 들어오면 전체 게시글 반환
    
    // 개별 게시글 조회
    AskObject askFind(String askpostid);
    //-- 게시글ID가 오면 AskObject를 반환

    // 유저 게시글 전체 조회 : List<AskPostObject>
    List<AskObject> askUserFindAll(String id);
    //-- 유저ID가 오면 List<AskObject>를 반환

    // 삭제 : int 반환값이 1이면 성공 , 반환값이 0이면 실패
    boolean askDelete(String askpostid, String id);
    //-- 게시글ID가 오면 성공여부를 반환

    // 수정 : 수정이 성공했는가 실패했는가?
    boolean askUpdate(String askpostid);

    // 답변게시글 개별조회

}
