package com.example.wearVillage.DAO.getChatPostCount;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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


        Map<String, String> param = Map.of("PostWriterId", PostWriterId);

        int countedRow = template.queryForObject(sql.toString(),param,Integer.class);
        log.info("countedRow = {}",countedRow);
        return countedRow;
    }
}
