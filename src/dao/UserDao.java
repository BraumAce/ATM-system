package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import model.UserInfo;

public class UserDao extends BaseDao{

	static Connection conn;
	
	//添加UserInfo
	public static boolean executeInsert(UserInfo user) {
		conn = getConn();
		int state = 0;
		String sql = "insert into userinfo(customerID,customerName,PID,telephone,address) values(?, ?, ?, ?, ?)";
		try {
			PreparedStatement pStatement = conn.prepareStatement(sql);
			pStatement.setInt(1,user.getCustomerID());
			pStatement.setString(2, user.getCustomerName());
			pStatement.setString(3, user.getPID());
			pStatement.setString(4, user.getTelephone());
			pStatement.setString(5, user.getAddress());
			
			state=pStatement.executeUpdate();
		}
		catch (SQLIntegrityConstraintViolationException e) {
			state = -1;
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
		if (state != 1) return false;
		return true;
	}
	
	//根据用户ID来删除该UserInfo
	public static boolean executeDelete(int customerID) {
		conn = getConn();
		int state = 0;
		String sql = "delete from userinfo where customerID=?";
		try {
			PreparedStatement pStatement = conn.prepareStatement(sql);
			pStatement.setInt(1,customerID);
			state=pStatement.executeUpdate();
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
		if (state != 1) return false;
		return true;
	}
	
	//查询此表中最大的用户ID，以供提供新注册用户ID
	public static int executeQueryMaxID() {
		conn = getConn();
		int state = 0;
		ResultSet rs;
		String sql = "select max(customerID) from userinfo";
		try {
			Statement statement = conn.createStatement();
			rs=statement.executeQuery(sql);
			while(rs.next()) {
				state = rs.getInt(1);
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
		return state;
	}
	
	public static boolean executeVerify(int customerID,String PID,String customerName,String telephone) {
		conn = getConn();
		boolean state = false;
		String sql = "SELECT PID,customerName,telephone FROM userinfo WHERE customerID=?";
		try {
			PreparedStatement pStatement = conn.prepareStatement(sql);
			pStatement.setInt(1,customerID);
			ResultSet rs=pStatement.executeQuery();
			if (rs.next()) {
				String nowPID,nowCName,nowTelephone;
				nowPID = rs.getString("PID");
				nowCName=rs.getString("customerName");
				nowTelephone=rs.getString("telephone");
				if (nowPID.equals(PID) && nowCName.equals(customerName) && nowTelephone.equals(telephone)) {
					state = true;
				}
				else state = false;
			}
			else{
				state = false;
			}
			rs.close();
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
		return state;
	}
	public static UserInfo executeGetAllUserInfo(String cardID) {
		conn = getConn();
		UserInfo result = null;
		String sql = "SELECT * FROM userinfo WHERE customerID=(SELECT customerID FROM cardinfo WHERE cardID=?)";
        //String sql="SELECT customerID,customerName,pID,telephone,address FROM Card_User WHERE cardID=?";
		try {
			PreparedStatement pStatement = conn.prepareStatement(sql);
			pStatement.setString(1,cardID);
			ResultSet rs=pStatement.executeQuery();
			if (rs.next()) {
				result = new UserInfo(rs.getInt("customerID"),
									  rs.getString("customerName"),
									  rs.getString("pID"),
									  rs.getString("telephone"),
									  rs.getString("address")) ;
			}
			rs.close();
			//验证是否存在该卡，存在state为0，不存在为1
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
		return result;
	}
}
