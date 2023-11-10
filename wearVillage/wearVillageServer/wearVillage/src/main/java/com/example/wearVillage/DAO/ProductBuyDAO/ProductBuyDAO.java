package com.example.wearVillage.DAO.ProductBuyDAO;

import com.example.wearVillage.PostData;

public interface ProductBuyDAO {

    ProductBuyForm readyToTrade(ProductBuyForm productBuyForm);
    ProductBuyForm productPrice(String tradeId);

    int howMuchMiliage(String id);

    String trade(ProductBuyForm productBuyForm);

}
