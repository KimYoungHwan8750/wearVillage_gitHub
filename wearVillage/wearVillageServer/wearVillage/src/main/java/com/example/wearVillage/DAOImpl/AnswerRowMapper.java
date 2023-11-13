package com.example.wearVillage.DAOImpl;

import com.example.wearVillage.Entity.AnswerObject;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AnswerRowMapper implements RowMapper<AnswerObject> {
  @Override
  public AnswerObject mapRow(ResultSet rs, int rowNum) throws SQLException {
    AnswerObject answerObject = new AnswerObject();
    answerObject.setANSWER_ID(rs.getString("ANSWER_ID"));
    answerObject.setTITLE(rs.getString("TITLE"));
    answerObject.setBODY(rs.getString("BODY"));
    answerObject.setTIME_LOG(rs.getString("TIME_LOG"));

    return answerObject;
  }
}
