package com.example.wearVillage.DAOImpl;

import com.example.wearVillage.Entity.AskObject;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AskRowMapper implements RowMapper<AskObject> {
  @Override
  public AskObject mapRow(ResultSet rs, int rowNum) throws SQLException {
    AskObject askObject = new AskObject();
    askObject.setAskpostid(rs.getString("askpostid"));
    askObject.setId(rs.getString("id"));
    askObject.setTitle(rs.getString("title"));
    askObject.setBody(rs.getString("body"));
    askObject.setTimelog(rs.getString("timelog"));
    askObject.setAnswerbody(rs.getString("answerbody"));
    askObject.setGubun(rs.getString("gubun"));
    askObject.setEmail(rs.getString("email"));

    return askObject;
  }
}
