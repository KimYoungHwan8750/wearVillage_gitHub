package com.example.wearVillage.testArea.findByEmailProject.Controller.Service;

import com.example.wearVillage.Entity.USER_INFO;
import com.example.wearVillage.testArea.findByEmailProject.Controller.DAO.DAO.FindUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service

public class FindUserIdSVC {
    private final FindUserId findUserId;
    public Optional<USER_INFO> FindByUserId(String Email){

        return findUserId.findByEMAIL(Email);
    }
}
