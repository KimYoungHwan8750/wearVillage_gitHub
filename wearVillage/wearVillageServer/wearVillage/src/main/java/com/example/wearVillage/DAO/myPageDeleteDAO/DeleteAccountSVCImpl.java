package com.example.wearVillage.DAO.myPageDeleteDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteAccountSVCImpl implements DeleteAccountSVC {

    private final DeleteAccountDAO deleteAccountDAO;
    @Override
    public Boolean DeleteAccount(String id) {
        return deleteAccountDAO.DeleteAccount(id);
    }
}
