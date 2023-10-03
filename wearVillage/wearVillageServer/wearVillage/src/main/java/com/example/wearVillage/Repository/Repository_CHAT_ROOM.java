package com.example.wearVillage.Repository;

import com.example.wearVillage.Entity.CHAT_ROOM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface Repository_CHAT_ROOM extends JpaRepository<CHAT_ROOM,Long> {
    // 자신의 닉네임으로 된 채팅방 전체 조회
    List<CHAT_ROOM> findById(String id);

    // 해당 게시글에 나의 채팅방이 있는지 조회
    Boolean existsById(String id);

}
