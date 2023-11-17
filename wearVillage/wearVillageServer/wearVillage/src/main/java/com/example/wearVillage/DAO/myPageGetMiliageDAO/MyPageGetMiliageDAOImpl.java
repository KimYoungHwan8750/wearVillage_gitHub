package com.example.wearVillage.DAO.myPageGetMiliageDAO;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
        int miliage;
        try {
            miliage = template.queryForObject(sql.toString(), param, int.class);
            log.info("miliage={}", miliage);
        } catch (EmptyResultDataAccessException e){
            return 0;
        }
        return miliage;
    }

    @Override
    public int withDrawMiliage(String ID,int drawMiliage){
        String sql = "UPDATE USER_WALLET SET MILIAGE = MILIAGE - :MILIAGE WHERE ID = :ID";
        Map<String, Object> params = new HashMap<>();
        params.put("MILIAGE",drawMiliage);
        params.put("ID",ID);
        int miliage;
        try {
            miliage = template.update(sql, params);
        }catch (EmptyResultDataAccessException e){
            return -1;
        }
        return miliage;
    }
}
