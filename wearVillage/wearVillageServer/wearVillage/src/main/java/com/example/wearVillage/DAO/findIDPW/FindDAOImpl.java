package com.example.wearVillage.DAO.findIDPW;

import com.example.wearVillage.DTO.GmailDto;
import com.example.wearVillage.Service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FindDAOImpl implements FindDAO {

    private final NamedParameterJdbcTemplate template;
    private final EmailService mailService;
    private final HttpSession session;

    /**
     * <span style="color:white">박정연</span>
     * @param email 아이디찾기 email 입력받음
     * @return 찾아진 ID값 반환 혹은 "조회되는 정보가 없습니다" 반환
     */
    @Override
    public String findId(String email) {
        StringBuffer sql = new StringBuffer();
        sql.append("select ID from USER_INFO ");
        sql.append( "where email = :email ");

        Map<String,String> param = Map.of("email",email);

        try{
            String findedID = template.queryForObject(sql.toString(),param,String.class);
            return findedID;
        } catch (EmptyResultDataAccessException ex){
            return "조회되는 정보가 없습니다.";
        }
    }

    /**
     * <span style="color:white;">박정연</span>
     * @param id 비밀번호 찾기 form의 id
     * @param email 비밀번호 찾기 form의 email
     * @return 조회될 경우 비밀번호를 반환, 아닌 경우 "조회되는 정보가 없습니다."
     */
    @Override
    public String findPwByIdAndPw(String id, String email) {
        StringBuffer sql = new StringBuffer();
        sql.append("select PW from USER_INFO ");
        sql.append( "where id = :id and email = :email ");

        Map<String,String> param = Map.of("id",id,"email",email);

        try{
            String findedPw = template.queryForObject(sql.toString(),param,String.class);
            return findedPw;
        } catch (EmptyResultDataAccessException ex){
            return "조회되는 정보가 없습니다.";
        }
    }

    @Override
    public void setTempPw(String email, String id, String pw) {
        StringBuffer sql = new StringBuffer();
        sql.append("update USER_INFO set PW = :pw ");
        sql.append( "where email = :email and id = :id ");

        Map<String,String> param = Map.of("email",email,"id",id,"pw",pw);

        int updatedRow = template.update(sql.toString(),param);
        log.info("updatedRow = {}",updatedRow);
        log.info("임시 비밀번호발급, 임시 비밀번호는={}입니다",pw);
    }
}
