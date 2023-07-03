package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDao {

	private static final String URL="jdbc:mysql://localhost:3306/banksystem?useSSL=false&serverTimezone=GMT";
	private static final String NAME="root"; //登录名
	private static final String PASSWORD="123456"; //密码
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver"; //驱动程序名
	
	public static Connection getConn() {
		Connection conn = null;
		try {
			Class.forName(DRIVER);
			conn= DriverManager.getConnection(URL,NAME,PASSWORD);
		}
        catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
    public static void close(Statement stmt, Connection conn) throws Exception{
        if(stmt!=null){  
            stmt.close();  
            if(conn!=null){  
                conn.close();  
            }
        }  
    }  

    public static void close(CallableStatement cstmt, Connection conn) throws Exception{  
        if(cstmt!=null){  
            cstmt.close();  
            if(conn!=null){  
                conn.close();  
            }  
        }  
    }  
      
    public static void close(PreparedStatement pstmt, Connection conn) throws SQLException{  
        if(pstmt!=null){  
            pstmt.close();  
            if(conn!=null){  
                conn.close();  
            }  
        }  
    }  
	
    public void close(ResultSet rs,PreparedStatement pstmt, Connection conn) throws Exception{  
        if(rs!=null){  
            rs.close();  
            if(pstmt!=null){  
                pstmt.close();  
                if(conn!=null){  
                    conn.close();  
                }
            }  
        }  
    }  
}
