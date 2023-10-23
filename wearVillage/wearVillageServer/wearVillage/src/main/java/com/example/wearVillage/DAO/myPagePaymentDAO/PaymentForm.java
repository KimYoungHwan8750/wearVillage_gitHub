package com.example.wearVillage.DAO.myPagePaymentDAO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import oracle.sql.DATE;

import java.time.LocalDateTime;

@Data
public class PaymentForm {
    private String AID;
    private String ID;
    private String TID;
    private String EMAIL;
    private Integer total;
    private LocalDateTime createdTime;
    private LocalDateTime approvedTime;
}

//create table payment_data(
//    ID varchar2(40) primary key,
//    EMAIL varchar2(60),
//    aid varchar2(100),
//    tid varchar2(100),
//    total NUMBER,
//    createdTime TIMESTAMP,
//    approveTime TIMESTAMP
//);

//                    body : {
//                        aidPayment : aidPayment,
//                        userIdPayment : userIdPayment,
//                        tidPayment : tidPayment,
//                        emailPayment : emailPayment,
//                        totalPayment : totalPayment,
//                        createdTimePayment : createdTimePayment,
//                        approveTimePayment : approveTimePayment,
//                    },
