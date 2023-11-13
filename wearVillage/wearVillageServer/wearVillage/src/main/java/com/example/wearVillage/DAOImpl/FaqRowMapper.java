package com.example.wearVillage.DAOImpl;

import com.example.wearVillage.Entity.AskObject;
import com.example.wearVillage.Entity.FaqObject;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FaqRowMapper implements RowMapper<FaqObject> {
  @Override
  public FaqObject mapRow(ResultSet rs, int rowNum) throws SQLException {
    FaqObject faqObject = new FaqObject();
    faqObject.setFaqpostid(rs.getString("faqpostid"));
    faqObject.setTitle(rs.getString("title"));
    faqObject.setBody(rs.getString("body"));
    return faqObject;
  }
}
