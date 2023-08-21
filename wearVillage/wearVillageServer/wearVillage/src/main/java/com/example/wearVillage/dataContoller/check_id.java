package com.example.wearVillage.dataContoller;

import com.example.wearVillage.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class check_id {

    public static boolean check_id(String id) {
        Connection conn = null; // DB연결된 상태(세션)을 담은 객체
        PreparedStatement pstm = null;  // SQL 문을 나타내는 객체
        ResultSet rs = null;  // 쿼리문을 날린것에 대한 반환값을 담을 객체
        boolean check_id = false;

        try {
            // SQL 문장을 만들고 만약 문장이 질의어(SELECT문)라면
            // 그 결과를 담을 ResulSet 객체를 준비한 후 실행시킨다.
            String query_text = "SELECT ID FROM USERINFO";

            conn = DBConnection.getConnection();
            pstm = conn.prepareStatement(query_text);
            rs = pstm.executeQuery();


            while(rs.next()){
                //int empno = rs.getInt("empno"); 숫자 대신 컬럼 이름을 적어도 된다.
                String ID = rs.getString("ID");
                if(id.equals(ID)){
                    check_id=true;
                }
            }

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
        return check_id;
    }
}