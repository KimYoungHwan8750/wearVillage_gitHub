package com.example.wearVillage.dataContoller;

import com.example.wearVillage.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class selectBoardQuery {

    public static void selectBoardQuery(String POST_ID, String POST_TITLE) {
        // BOARD 테이블에 넣을 데이터의 속성명
        Connection conn = null; // DB연결된 상태(세션)을 담은 객체
        PreparedStatement pstm = null;  // SQL 문을 나타내는 객체
        ResultSet rs = null;  // 쿼리문을 날린것에 대한 반환값을 담을 객체


        try {
            // SQL 문장을 만들고 만약 문장이 질의어(SELECT문)라면
            // 그 결과를 담을 ResulSet 객체를 준비한 후 실행시킨다.
//            String query_board = "INSERT INTO BOARD VALUES('"+POST_ID+"','"+POST_TITLE+"','null','"+POST_TAG_BIG+"','"+POST_TAG_BIG+"','"+POST_TAG_MIDDLE+"','"+POST_INFO+"')";
            String query_board = "INSERT INTO BOARD VALUES('"+POST_ID+"','"+POST_TITLE+"')";
            // BOARD 테이블에 넣을 데이터의 속성명
            String display = "SELECT * FROM BOARD";

            conn = DBConnection.getConnection();
            pstm = conn.prepareStatement(query_board);
            rs = pstm.executeQuery();

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