package com.example.wearVillage.DAOImpl;

import com.example.wearVillage.DAO.MyPageDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("MyPageDAOImpl")
@RequiredArgsConstructor
@Slf4j

public class MyPageDAOImpl implements MyPageDAO {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public boolean changePassword(String id, String oldPw,String newPw){
        int count = jdbcTemplate.update("UPDATE USER_INFO SET PW=? WHERE id =? AND PW=?",new Object[]{newPw,id,oldPw});
        return count==1;
    }
}
