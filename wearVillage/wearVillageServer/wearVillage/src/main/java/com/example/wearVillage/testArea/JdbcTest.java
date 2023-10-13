package com.example.wearVillage.testArea;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcTest {

    private static JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        // create a data source
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("oracle.jdbc.OracleDriver"); // adjust for your DBMS
        dataSource.setUrl("jdbc:oracle:thin:@wearvillage.c38c15agkmuv.ap-northeast-2.rds.amazonaws.com:1521:VILLAGE"); // adjust for your database URL
        dataSource.setUsername("admin"); // adjust for your username
        dataSource.setPassword("admin12345"); // adjust for your password

        jdbcTemplate = new JdbcTemplate(dataSource);

        List<Map<String, Object>> test = jdbcTemplate.query("SELECT * FROM USER_INFO",new RowMapper<Map<String,Object>>(){
            @Override
            public Map<String,Object> mapRow(ResultSet rs, int rowNum) throws SQLException{
                Map<String,Object> testMap = new HashMap<>();
                testMap.put("ID",rs.getString("ID"));
                testMap.put("PW",rs.getString("PW"));
                testMap.put("EMAIL",rs.getString("EMAIL"));
                return testMap;
            }
        });
        List<Map<String,Object>> testCopy = test.stream().map(
                str->{
                    Map<String,Object> mapTest = new HashMap<>();
                    if(str.get("ID").equals("testid")){
                        mapTest.put("ID", "테스트 아이디는 이거에용!");
                    }else{
                        mapTest.put("ID",str.get("ID")+"는 테스트 아이디가 아니에용!");
                    }
                return mapTest;}
        ).toList();



    }
}
