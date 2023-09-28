package com.example.wearVillage.testArea;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MyRowMapper implements RowMapper<KYH> {
    @Override
    public KYH mapRow(ResultSet rs, int rowNum) throws SQLException {
        KYH kyh = new KYH();
        kyh.setAge(rs.getString("PW"));
        kyh.setName(rs.getString("ID"));
        return kyh;
    }
}
