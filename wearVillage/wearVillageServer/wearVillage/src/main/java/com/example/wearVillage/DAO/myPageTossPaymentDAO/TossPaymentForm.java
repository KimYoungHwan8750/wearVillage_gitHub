package com.example.wearVillage.DAO.myPageTossPaymentDAO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TossPaymentForm {
    @JsonProperty("tossName")
    private String tossName;
    @JsonProperty("tossEmail")
    private String tossEmail;
    @JsonProperty("tossAmount")
    private Long tossAmount;
    @JsonProperty("tossTradeTime")
    private String tossTradeTime;
    @JsonProperty("tossMid")
    private String tossMid;
}
