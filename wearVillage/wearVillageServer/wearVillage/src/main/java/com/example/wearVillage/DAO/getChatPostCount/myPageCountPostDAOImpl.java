package com.example.wearVillage.DAO.getChatPostCount;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class myPageCountPostDAOImpl implements myPageCountPostDAO{

    private final NamedParameterJdbcTemplate template;
    @Override
    public int countPost(String ID, String PostWriterId) {
        StringBuffer sql = new StringBuffer();
        sql.append("select count(*) ");
        sql.append(  "from CHAT_ROOM ");
        sql.append( "where Member1 = :PostWriterId or Member2 = :PostWriterId");

        int countedRow;
        Map<String, String> param = Map.of("PostWriterId", PostWriterId);
        try {
             countedRow = template.queryForObject(String.valueOf(sql), param, Integer.class);
            log.info("countedRow = {}", countedRow);
        } catch (EmptyResultDataAccessException e){
            return 0;
        }
        return countedRow;
    }
}
