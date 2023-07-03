package dao;

import java.sql.*;
import java.util.ArrayList;

import model.TransInfo;

import static dao.BaseDao.getConn;

public class TransDao {
    static Connection conn;
    public static ArrayList<TransInfo> executeGetAllRecords(String cardID) {
        conn = getConn();
        ArrayList<TransInfo> results=new ArrayList<TransInfo>();
        ResultSet rs;
        String sql = "SELECT * FROM transinfo WHERE cardID=?";
        try {
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setString(1,cardID);
            rs=pStatement.executeQuery();
            while(rs.next()) {
            	TransInfo transInfo = new TransInfo(
            			rs.getDate("transDate"),
                        rs.getString("cardID"),
            			rs.getString("otherCardID"),
                        rs.getString("transType"),
            			rs.getFloat("transMoney"),
                        rs.getString("remark"));
            	results.add(transInfo);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                conn.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}
