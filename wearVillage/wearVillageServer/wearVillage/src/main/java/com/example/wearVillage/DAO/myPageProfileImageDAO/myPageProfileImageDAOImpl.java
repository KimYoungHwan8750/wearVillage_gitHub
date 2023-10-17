package com.example.wearVillage.DAO.myPageProfileImageDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class myPageProfileImageDAOImpl implements myPageProfileImageDAO{

    private final NamedParameterJdbcTemplate template;

    @Override
    public int changeProfileImage(String ID, String PROFILEIMAGE) {
        StringBuffer sql = new StringBuffer();
        sql.append("update USER_INFO ");
        sql.append("set PROFILEIMG = :PROFILEIMAGE ");
        sql.append("where ID = :ID");

        log.info("sql={}",sql);
        log.info("dao에 온 purl={}",PROFILEIMAGE);
        log.info("dao에 온 ID={}",ID);
        Map<String, String> param = Map.of("ID",ID,"PROFILEIMAGE",PROFILEIMAGE);

        int updatedRow = template.update(sql.toString(), param);
        return updatedRow;
    }
}
