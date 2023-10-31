package com.example.wearVillage.testArea.findByEmailProject.Controller.DAO.DAO;

import com.example.wearVillage.Entity.USER_INFO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface FindUserId extends JpaRepository<USER_INFO,String> {
    Optional<USER_INFO> findByEMAIL(String EMAIL);
}
