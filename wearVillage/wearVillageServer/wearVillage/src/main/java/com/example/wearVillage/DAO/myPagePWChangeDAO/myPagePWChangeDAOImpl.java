package com.example.wearVillage.DAO.myPagePWChangeDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class myPagePWChangeDAOImpl implements myPagePWChangeDAO{

    private final NamedParameterJdbcTemplate template;

    @Override
    public String getPWbyID(String ID) {
        String sql = new String();
        sql = "select PW from USER_INFO where ID = :ID ";

        Map<String,String> param = Map.of("ID",ID);
        log.info("PARAM={}",param);


        String findedPW = template.queryForObject(sql,param,String.class);
        log.info("DAOPW{}",findedPW);

        return findedPW;
    }

    @Override
    public int changePW(myPagePWChangeDTO dto) {
        StringBuffer sql = new StringBuffer();
        sql.append("update USER_INFO ");
        sql.append(   "set PW = :newPW ");
        sql.append( "where ID = :ID ");
        sql.append(   "and PW = :PW ");

        Map<String, String> param = Map.of("ID",dto.getUserId(),"PW",dto.getPW(),"newPW",dto.getNewPW());

        log.info("DAO{}",dto.getUserId());
        log.info("DAOPW{}",dto.getPW());
        log.info("DAONEWPW{}",dto.getNewPW());

        int changedPWRow = template.update(sql.toString(),param);
        log.info("changedRow = {}",changedPWRow);
        return changedPWRow;
    }
}
