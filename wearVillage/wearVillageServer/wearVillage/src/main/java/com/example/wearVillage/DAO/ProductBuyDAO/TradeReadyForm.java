package com.example.wearVillage.DAO.ProductBuyDAO;

import lombok.Data;

@Data
public class TradeReadyForm {
    private String postId;
    private String buyerId;
    private String sellerId;
    private String price;
    private String rentDefaultPrice;
    private String rentDayPrice;
}
