package com.example.wearVillage.DAO.myPagePaymentDAO;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentSVCImpl implements PaymentSVC{

    private final PaymentDAO paymentDAO;
    public int paymentDateToDB(PaymentForm paymentForm){
        return paymentDAO.paymentDateToDB(paymentForm);
    }

}
