package com.example.wearVillage.DAO.myPageTossPaymentDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TossPaymentSVCImpl implements TossPaymentSVC{
    private final TossPaymentDAO tossPaymentDAO;

    @Override
    public int tossPaymentToDB(TossPaymentForm tossPaymentForm) {
        return tossPaymentDAO.tossPaymentToDB(tossPaymentForm);
    }
}
