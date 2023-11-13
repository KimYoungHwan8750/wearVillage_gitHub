package com.example.wearVillage.DAO.ProductBuyDAO;

import com.example.wearVillage.PostData;

import java.util.List;

public interface ProductBuyDAO {

    ProductBuyForm readyToTrade(ProductBuyForm productBuyForm);
    ProductBuyForm productPrice(String tradeId);

    int howMuchMiliage(String id);

    String trade(ProductBuyForm productBuyForm);

    String trade2(ProductRentForm productRentForm);

    int tradeFinal(ProductFinalForm productFinalForm);

    List<RentData> checkPerDay();

    String checkFinalDay(String tid);

    String getMiddleMiliage(String tid);

    String whoIsSeller(String tid);

    String whoIsBuyer(String tid);

    int returnMiliage(String id, int miliage);

    int completeTrade(String tradeId);

    void sellerCompleteTrade(String tradeId);
}
