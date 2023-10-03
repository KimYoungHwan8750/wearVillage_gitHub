package com.example.wearVillage.DeleteEvent;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DeleteDAOImpl implements DeleteDAO {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public int deleteById(Long postId) {
        String sql = "delete from posting_table where post_id = :postId ";
        int deletedRow = namedParameterJdbcTemplate.update(sql, Map.of("postId", postId));
        log.info("삭제호출됨 postId={}",postId);
        return deletedRow;
    }
}
