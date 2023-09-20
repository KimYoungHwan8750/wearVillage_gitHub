package com.example.wearVillage.dataController;
import com.example.wearVillage.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class postToOracle {
  public static void postdataToOracle(String POST_ID, String POST_WRITER_ID, String POST_SUBTITLE, String POST_PRICE, String POST_RENT_DEFAULT_PRICE, String POST_RENT_DAY_PRICE, String POST_TAG_TOP, String POST_TAG_MIDDLE, String POST_MAP_INFO,String POST_TEXT, String POST_DATE, String POST_MODIFY_DATE) {
    Connection conn = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;

    try{
      String query_text = "INSERT INTO POSTING_TABLE VALUES('"+POST_ID+"','"+POST_WRITER_ID+"','"+POST_SUBTITLE+"','"+POST_PRICE+"','"+POST_RENT_DEFAULT_PRICE+"','"+POST_RENT_DAY_PRICE+"',"+POST_TAG_TOP+"','"+POST_TAG_MIDDLE+"','"+POST_MAP_INFO+"','"+POST_TEXT+"','"+POST_DATE+"','"+POST_MODIFY_DATE+"')";

      conn = DBConnection.getConnection();
      pstm = conn.prepareStatement(query_text);
      rs = pstm.executeQuery();
    } catch (SQLException sqle) {
      System.out.println(sqle.getMessage());
      sqle.printStackTrace();

    }finally{
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
  
