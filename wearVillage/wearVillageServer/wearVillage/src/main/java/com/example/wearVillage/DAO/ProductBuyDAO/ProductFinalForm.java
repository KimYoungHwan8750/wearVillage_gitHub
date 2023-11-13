package com.example.wearVillage.DAO.ProductBuyDAO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
public class ProductFinalForm {
    private Long tradeId;
    private String postId;
    private String buyerId;
    private String sellerId;
    private String price;
    private String rentDefaultPrice;
    private String rentDayPrice;
    private String middleMiliage;
    private String rentStartDay;
    private String rentFinishDay;
    private String finalDay;
    private LocalDateTime tradeDate;
    public ProductFinalForm(ProductRentForm productRentForm, String middleMiliage, String finalDay){
        this.tradeId= productRentForm.getTradeId();
        this.postId=productRentForm.getPostId();
        this.buyerId=productRentForm.getBuyerId();
        this.sellerId=productRentForm.getSellerId();
        this.price=productRentForm.getPrice();
        this.rentDefaultPrice= productRentForm.getRentDefaultPrice();
        this.rentDayPrice= productRentForm.getRentDayPrice();
        this.middleMiliage = middleMiliage;
        this.rentStartDay = productRentForm.getRentStartDay();
        this.rentFinishDay= productRentForm.getRentFinishDay();
        this.finalDay = finalDay;
        this.tradeDate = productRentForm.getTradeDate();
    }
}