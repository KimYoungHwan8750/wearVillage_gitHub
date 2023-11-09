package com.example.wearVillage.DAO.ProductBuyDAO;

import com.example.wearVillage.PostData;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.DataException;
import org.jetbrains.annotations.NotNull;
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
    public ProductBuyForm readyToTrade(@NotNull ProductBuyForm productBuyForm) {
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

    public int howMuchMiliage(String id){
        StringBuffer sql = new StringBuffer();
        sql.append("select miliage from user_wallet where id = :id ");
        Map<String,String> param = Map.of("id",id);

        try{
            int miliage = template.queryForObject(sql.toString(),param,int.class);
            return miliage;
        } catch (DataException e){
            return -1;
        }

    }

    @Override
    public String trade(ProductBuyForm productBuyForm) {
        int buyerMiliage = howMuchMiliage(productBuyForm.getBuyerId());
        int sellerMiliage = howMuchMiliage(productBuyForm.getSellerId());
        int price = Integer.valueOf(productBuyForm.getPrice());
        //SVC단
        // 구매가 불가능한 경우
        // 1. 구매자의 마일리지가 부족한 경우
        if(price > buyerMiliage){
            return "마일리지가 부족합니다.";
        } else {
            //구매가 가능한 경우
            StringBuffer sql = new StringBuffer();
            //구매자의 마일리지를 차감하는 쿼리문
            sql.append("update user_wallet ");
            sql.append(   "set miliage = (miliage-:price) ");
            sql.append( "where id = :buyerId ");

            Map<String,Object> param = Map.of("price",price,"buyerId",productBuyForm.getBuyerId());
            //마일리지 차감
            int a = template.update(sql.toString(),param);
            //차감 성공한 경우
            if (a==1){
                //판매자의 마일리지를 늘려주는 쿼리문
                StringBuffer plusSql = new StringBuffer();
                plusSql.append("update user_wallet ");
                plusSql.append(   "set miliage = (miliage+:price) ");
                plusSql.append( "where id = :sellerId ");

                Map<String,Object> plusParam = Map.of("price",price,"sellerId",productBuyForm.getSellerId());
                //마일리지 추가
                int b = template.update(plusSql.toString(),plusParam);
                //마일리지가 추가된 경우
                if(b==1){
                    return "구매 완료";
                } else {
                    return "구매 실패";
                }

                //차감 실패한 경우
            } else {
                return "오류가 있습니다. 관리자에게 문의해주세요.";
            }
        }
    }
}