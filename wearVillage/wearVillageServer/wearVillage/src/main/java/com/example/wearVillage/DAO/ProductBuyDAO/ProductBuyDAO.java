package com.example.wearVillage.DAO.ProductBuyDAO;

import com.example.wearVillage.PostData;

public interface ProductBuyDAO {

    ProductBuyForm readyToTrade(ProductBuyForm productBuyForm);
    ProductBuyForm productPrice(String tradeId);

    int howMuchMiliage(String id);

    String trade(ProductBuyForm productBuyForm);

    String trade2(ProductRentForm productRentForm);

    int tradeFinal(ProductFinalForm productFinalForm);

}
