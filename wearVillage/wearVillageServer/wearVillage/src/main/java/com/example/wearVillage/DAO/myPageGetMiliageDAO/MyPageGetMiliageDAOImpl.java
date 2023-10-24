package com.example.wearVillage.DAO.myPageGetMiliageDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MyPageGetMiliageDAOImpl implements MyPageGetMiliageDAO{
    private final NamedParameterJdbcTemplate template;

    @Override
    public int getMiliage(String userId,MyPageGetMiliageForm myPageGetMiliageForm) {
        StringBuffer sql = new StringBuffer();
        sql.append("select miliage from USER_WALLET where ID = :ID ");
        Map<String,String> param = Map.of("ID",userId);

        int miliage = template.queryForObject(sql.toString(),param,int.class);
        log.info("miliage={}",miliage);

        return miliage;
    }
}
