package com.example.wearVillage.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatDAOJpaImpl extends JpaRepository<ChatEntity,Integer> {
}
