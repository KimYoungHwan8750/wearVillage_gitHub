package com.example.wearVillage.dataContoller;

import com.example.wearVillage.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginCheck {
    /* 아이디와 비밀번호 확인 후 성공시 True 실패시 False를 반환*/
    public static boolean login_check(String login_id, String login_password) {
        Connection conn = null; // DB연결된 상태(세션)을 담은 객체
        PreparedStatement pstm = null;  // SQL 문을 나타내는 객체
        ResultSet rs = null;  // 쿼리문을 날린것에 대한 반환값을 담을 객체
        String ID=null;
        String PASSWORD=null;
        boolean success_check=false;

        try {
            // SQL 문장을 만들고 만약 문장이 질의어(SELECT문)라면
            // 그 결과를 담을 ResulSet 객체를 준비한 후 실행시킨다.
            String query_text = "SELECT * FROM USERINFO";

            conn = DBConnection.getConnection();
            pstm = conn.prepareStatement(query_text);
            rs = pstm.executeQuery();

            while(rs.next()){
                //int empno = rs.getInt("empno"); 숫자 대신 컬럼 이름을 적어도 된다.
                ID = rs.getString("ID");
                PASSWORD = rs.getString("PASSWORD");
                if(login_id.equals(ID)&&login_password.equals(PASSWORD)){
                    success_check=true;
                }
            }

        } catch (SQLException sqle) {
            System.out.println("SELECT문에서 예외 발생");
            sqle.printStackTrace();

        } finally {
            // DB 연결을 종료한다.
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        return success_check;
        }
    }
}
