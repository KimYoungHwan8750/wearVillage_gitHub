package com.example.wearVillage.DAOImpl;

import com.example.wearVillage.DAO.FaqPostDAO;
import com.example.wearVillage.Entity.AskObject;
import com.example.wearVillage.Entity.FaqObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Slf4j
@RequiredArgsConstructor
public class FaqPostDAOImpl implements FaqPostDAO {

  private final NamedParameterJdbcTemplate template;


  // 목록 (다중 행)
  @Override
  public List<FaqObject> faqFindAll() {
    StringBuffer sql = new StringBuffer();
    sql.append(" select faqpostid, title, body ");
    sql.append(" from faqpost ");

    FaqRowMapper faqRowMapper = new FaqRowMapper();
    List<FaqObject> list = template.query(sql.toString(), faqRowMapper);
    return list;
  }

  // 개별조회 (단일 행) -- FAQPOSTID 인풋
  @Override
  public FaqObject faqFind(String faqpostid) {
    StringBuffer sql = new StringBuffer();
    sql.append("  select faqpostid, title, body ");
    sql.append("  from faqpost    ");
    sql.append("  where faqpostid = :faqpostid  ");

    FaqRowMapper faqRowMapper = new FaqRowMapper();
    MapSqlParameterSource param = new MapSqlParameterSource();
    param.addValue("faqpostid",faqpostid);
    return template.queryForObject(sql.toString(), param, faqRowMapper);
  }
}
