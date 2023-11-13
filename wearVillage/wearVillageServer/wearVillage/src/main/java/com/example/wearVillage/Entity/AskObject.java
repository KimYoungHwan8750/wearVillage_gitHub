package com.example.wearVillage.Entity;

import lombok.Data;

@Data
public class AskObject {

  //게시물 ID -> 시퀀스
  private String askpostid;

  // 유저 아이디
  private String id;

  // 제목
  private String title;

  // 내용
  private String body;

  // 타임스탬프
  private String timelog;

  // 답변게시글 ID
  private String answerbody;

  // 게시글 구분
  private String gubun;

  // 이메일
  private String email;

}
