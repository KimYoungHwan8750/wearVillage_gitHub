package com.example.wearVillage.DAO.ProductBuyDAO;

import com.example.wearVillage.PostData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.DataException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductBuyDAOImpl implements ProductBuyDAO{
    private final NamedParameterJdbcTemplate template;


    @Override
    public ProductBuyForm readyToTrade(ProductBuyForm productBuyForm) {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into PRODUCT_TRADE_TABLE (");
        sql.append("tradeid,postid,buyerid,sellerid,price,rentdefaultprice, ");
        sql.append("rentdayprice, tradedate) ");
        sql.append("values ( ");
        sql.append("tid_seq.nextval, :postId, :buyerId, :sellerId, :price, ");
        sql.append(":rentDefaultPrice, :rentDayPrice, SYSTIMESTAMP) ");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("postId", productBuyForm.getPostId());
        params.addValue("buyerId", productBuyForm.getBuyerId());
        params.addValue("sellerId", productBuyForm.getSellerId());
        params.addValue("price", String.valueOf(productBuyForm.getPrice()));
        params.addValue("rentDefaultPrice", String.valueOf(productBuyForm.getRentDefaultPrice()));
        params.addValue("rentDayPrice", String.valueOf(productBuyForm.getRentDayPrice()));

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int updatedRow = template.update(sql.toString(),params, keyHolder,new String[]{"tradeId"});
        String searchedKey = String.valueOf(keyHolder.getKey());
        log.info("key={}",searchedKey);
        ProductBuyForm searchedForm = productPrice(searchedKey);

        return searchedForm;
    }

    @Override
    public ProductBuyForm productPrice(String tradeId) {
        StringBuffer sql = new StringBuffer();
        sql.append("select * ");
        sql.append(  "from PRODUCT_TRADE_TABLE ");
        sql.append( "where tradeId = :tradeId ");

        Map<String,String> param = Map.of("tradeId",tradeId);

        try{
            ProductBuyForm searchedData = template.queryForObject(sql.toString(),param, new BeanPropertyRowMapper<>(ProductBuyForm.class));
            return searchedData;
        } catch (DataException e){
            return null;
        }
    }
}