package com.example.wearVillage.DAO.myPagePaymentDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PaymentDAOImpl implements PaymentDAO{

    private final NamedParameterJdbcTemplate template;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public int paymentDateToDB(PaymentForm paymentForm) {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into payment_data (ID, EMAIL, aid, tid, total, createdTime, approveTime) ");
        sql.append("values (:ID, :EMAIL, :AID, :tid, :total, :createdTime, :approveTime) ");

        Map<String, Object> param = Map.of("ID", paymentForm.getID(),"EMAIL", paymentForm.getEMAIL(),"AID", paymentForm.getAID()
                ,"tid",paymentForm.getTID(),"total",paymentForm.getTotal(),"createdTime",paymentForm.getCreatedTime()
                ,"approveTime", paymentForm.getApprovedTime());

        int savedRow = template.update(sql.toString(),param);

        if(savedRow == 1){
            log.info("savedRow1 호출됨");

            String sql3="select MILIAGE from USER_WALLET where ID = :ID ";
            Map<String,String> param3 = Map.of("ID", paymentForm.getID());
            Long nowMiliage = template.queryForObject(sql3,param3,Long.class);
            Long newMiliage = nowMiliage + paymentForm.getTotal();

            log.info("nowMiliage:{}",nowMiliage);
            log.info("newMiliage:{}",newMiliage);

            String sql2="update USER_WALLET set MILIAGE = :miliage where ID = :ID ";
            log.info("sql2{}",sql2);
            Map<String,Object> param2 = Map.of("ID", paymentForm.getID(),"miliage",newMiliage);
            log.info("param2{}",param2);
            int updatedRow = template.update(sql2,param2);
            log.info("마일리지업데이트={}",updatedRow);

        }
        log.info("저장되었습니다.{}",savedRow);
        return savedRow;
    }

}
