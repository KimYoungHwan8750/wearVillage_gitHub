package com.example.wearVillage.testArea;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;

public class JdbcTest {

    private static JdbcTemplate jdbcTemplate;

    public static void main(String[] args){
        // create a data source
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("oracle.jdbc.OracleDriver"); // adjust for your DBMS
        dataSource.setUrl("jdbc:oracle:thin:@wearvillage.c38c15agkmuv.ap-northeast-2.rds.amazonaws.com:1521:VILLAGE"); // adjust for your database URL
        dataSource.setUsername("admin"); // adjust for your username
        dataSource.setPassword("admin12345"); // adjust for your password

        jdbcTemplate = new JdbcTemplate(dataSource);

        KYH kyh = jdbcTemplate.queryForObject("SELECT ID, PW FROM USER_INFO WHERE ID = 'testid'",new MyRowMapper());
        System.out.println(kyh.getName());
        System.out.println(kyh.getAge());
    }
}
