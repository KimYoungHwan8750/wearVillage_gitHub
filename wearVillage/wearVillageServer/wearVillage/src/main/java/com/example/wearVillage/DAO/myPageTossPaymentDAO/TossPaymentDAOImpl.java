package com.example.wearVillage.DAO.myPageTossPaymentDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TossPaymentDAOImpl implements TossPaymentDAO{
    private final NamedParameterJdbcTemplate template;

    @Override
    public int tossPaymentToDB(TossPaymentForm tossPaymentForm) {

        StringBuffer sql = new StringBuffer();
        sql.append("insert into TOSS_PAYMENT_DATA (tossName, tossEmail, tossAmount, tossTradeTime, tossMid) ");
        sql.append("values (:tossName, :tossEmail, :tossAmount, :tossTradeTime, :tossMid) ");

        Map<String,Object> param = Map.of("tossName",tossPaymentForm.getTossName(),"tossEmail",tossPaymentForm.getTossEmail(),
                "tossAmount",tossPaymentForm.getTossAmount(),"tossTradeTime",tossPaymentForm.getTossTradeTime(),"tossMid",tossPaymentForm.getTossMid());

        int savedRow = template.update(sql.toString(),param);

        if(savedRow == 1){
            log.info("savedRow1 호출됨");

            String sql1="select MILIAGE from USER_WALLET where ID = :ID ";
            Map<String,String> param3 = Map.of("ID", tossPaymentForm.getTossName());
            Long nowMiliage = template.queryForObject(sql1,param3,Long.class);
            Long newMiliage = nowMiliage + tossPaymentForm.getTossAmount();

            String sql2="update USER_WALLET set MILIAGE = :miliage where ID = :ID ";
            log.info("sql2{}",sql2);
            Map<String,Object> param2 = Map.of("ID", tossPaymentForm.getTossName(),"miliage",newMiliage);
            log.info("param2{}",param2);
            int updatedRow = template.update(sql2,param2);
            log.info("마일리지업데이트={}",updatedRow);
        }

        return savedRow;
    }
}
