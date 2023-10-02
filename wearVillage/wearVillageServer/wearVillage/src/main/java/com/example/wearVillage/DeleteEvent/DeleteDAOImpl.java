package com.example.wearVillage.DeleteEvent;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DeleteDAOImpl implements DeleteDAO {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Override
    public int deleteById(Long postId) {
        String sql = "delete from posting_table where post_id = :postId ";
        int deletedRow = namedParameterJdbcTemplate.update(sql, Map.of("postId", postId));
        return deletedRow;
    }
}
