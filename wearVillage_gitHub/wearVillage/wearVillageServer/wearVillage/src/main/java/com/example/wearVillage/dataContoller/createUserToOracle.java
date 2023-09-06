package com.example.wearVillage.dataContoller;

import com.example.wearVillage.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class createUserToOracle{
    /* 회원가입 양식에 입력된 정보들을 오라클에 저장하는 메소드*/
    public static void dataToOracle(String email, String userId, String userPassword){
        Connection conn = null; // DB연결된 상태(세션)을 담은 객체
        PreparedStatement pstm = null;  // SQL 문을 나타내는 객체
        ResultSet rs = null;  // 쿼리문을 날린것에 대한 반환값을 담을 객체

        try {
            // SQL 문장을 만들고 만약 문장이 질의어(SELECT문)라면
            // 그 결과를 담을 ResulSet 객체를 준비한 후 실행시킨다.
            String query_text = "INSERT INTO USERINFO VALUES('"+userId+"','"+userPassword+"','"+email+"')";

            conn = DBConnection.getConnection();
            pstm = conn.prepareStatement(query_text);
            rs = pstm.executeQuery();

/*            System.out.println("NAME   EMAIL");
            System.out.println("============================================");

            while(rs.next()){
                //int empno = rs.getInt("empno"); 숫자 대신 컬럼 이름을 적어도 된다.
                String name = rs.getString("NAME");
                String displayemail = rs.getString("EMAIL");
                String result = name + "//" + displayemail;
                System.out.println(result);
            }*/

        } catch (SQLException sqle) {
            System.out.println("SELECT문에서 예외 발생");
            sqle.printStackTrace();

        }finally{
            // DB 연결을 종료한다.
            try{
                if ( rs != null ){rs.close();}
                if ( pstm != null ){pstm.close();}
                if ( conn != null ){conn.close(); }
            }catch(Exception e){
                throw new RuntimeException(e.getMessage());
            }

        }
    }

}
