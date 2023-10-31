package com.example.wearVillage.DAO.myPageGetMyPostCountsDAO;

import groovy.transform.NamedParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class myPageGetMyPostCountsDAOImpl implements myPageGetMyPostCountsDAO{
    private final NamedParameterJdbcTemplate template;

    @Override
    public int findMyCounts(String ID) {
        StringBuffer sql = new StringBuffer();
        sql.append("select count(*) from POSTING_TABLE ");
        sql.append( "where ID = :ID ");

        Map<String,String> param = Map.of("ID",ID);

        int findedCount = template.queryForObject(sql.toString(),param,Integer.class);
        log.info("findedCount = {}",findedCount);
        return findedCount;
    }
}
