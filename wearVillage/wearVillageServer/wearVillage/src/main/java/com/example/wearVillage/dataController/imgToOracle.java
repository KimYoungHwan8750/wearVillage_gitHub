package com.example.wearVillage.dataController;
import com.example.wearVillage.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class imgToOracle {
  public static void imgdataToOracle(String uploadPath, String uuid, String fileName) {
    Connection conn = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;

    try{
      String query_text = "INSERT INTO CLOTH_IMAGE VALUES('"+fileName+"','"+uploadPath+"','"+uuid+"')";
      
      conn = DBConnection.getConnection();
      pstm = conn.prepareStatement(query_text);
      rs = pstm.executeQuery();
    } catch (SQLException sqle) {
      System.out.println(sqle.getMessage());
      System.out.println(sqle.getMessage());
      System.out.println(sqle.getMessage());
      System.out.println(sqle.getMessage());
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
  
