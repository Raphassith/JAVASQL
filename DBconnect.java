import java.sql.*;

public class DBconnect {
    
    private PreparedStatement pstmt;
    private String strConn = "jdbc:sqlite:database.db";
    
    private Connection conn(){
        try(Connection con = DriverManager.getConnection(strConn)){
            Statement stmt = con.createStatement();
            stmt.execute("PRAGMA encoding = 'UTF-8';");  // กำหนดให้สามารถรองรับ UTF8
            System.out.println("เชื่อมต่อ SQLite สำเร็จ!");
            return con;
        }catch(SQLException e){
            System.out.println("เกิดข้อผิดพลาด: " + e.getMessage()); 
            return null;
        }
    }
    
    
}
