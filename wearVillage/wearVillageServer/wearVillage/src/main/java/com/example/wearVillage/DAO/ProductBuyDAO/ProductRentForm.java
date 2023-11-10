package com.example.wearVillage.DAO.ProductBuyDAO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductRentForm {
    private Long tradeId;
    private String postId;
    private String buyerId;
    private String sellerId;
    private String price;
    private String rentDefaultPrice;
    private String rentDayPrice;
    private String rentStartDay;
    private String rentFinishDay;
    private LocalDateTime tradeDate;
}