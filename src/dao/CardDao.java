package dao;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.CardInfo;

public class CardDao extends BaseDao {

	static Connection conn;
	
	//添加CardInfo
	public static boolean executeInsert(CardInfo card) {
		conn=getConn();
		int state = 0;
		String sql = "INSERT INTO cardinfo(cardID,curType,savingType,openDate,openMoney,balance,"
				   + "pass,IsReportLoss,customerID) VALUES(?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement pStatement=conn.prepareStatement(sql);
			pStatement.setString(1,card.getCardID());
			pStatement.setString(2, card.getCurType());
			pStatement.setString(3, card.getSavingType());
			pStatement.setDate(4, card.getOpenDate());
			pStatement.setFloat(5, card.getOpenMoney());
			pStatement.setFloat(6, card.getBalance());
			pStatement.setString(7, card.getPass());
			pStatement.setString(8, card.isIsReportLoss()?"是":"否");
			pStatement.setInt(9, card.getCustomerID());
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
	
	//根据卡号来删除该CardInfo
	public static boolean executeDelete(String cardID) {
		conn = getConn();
		int state=0;
		String sql = "DELETE FROM cardinfo WHERE cardID=?";
		try {
			PreparedStatement pStatement = conn.prepareStatement(sql);
			pStatement.setString(1,cardID);
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
		if (state != 1)return false;
		return true;
	}

	//判断登录用户名和密码是否正确
	public static int executeLogin(String cardID,String pass) {
		conn = getConn();
		int state = 1; //0为成功，1为查无此卡，2为密码错误
		String sql = "SELECT cardID FROM cardinfo WHERE cardID=?";
		try {
			PreparedStatement pStatement=conn.prepareStatement(sql);
			pStatement.setString(1,cardID);
			ResultSet rs=pStatement.executeQuery();
			if (rs.next()) {
				//Retrieve by column name
				String cardIDForLogin  = rs.getString("cardID");
				//System.out.print("ID: " + id);
				state = 0;
            }
            else{
                state = 1;
            }
            rs.close();
            //验证是否存在该卡，存在state为0，不存在为1
            if (state == 0){
                sql = "SELECT pass FROM cardinfo WHERE cardID=?";
                pStatement = conn.prepareStatement(sql);
                pStatement.setString(1,cardID);
                rs=pStatement.executeQuery();
                if (rs.next()) {
                    String cardPassForLogin = rs.getString("pass");
                    if (cardPassForLogin.equals(pass)) {
                        state = 0;
                    }
                    else {
                        state = 2;
                    }
                }
                rs.close();
                //验证密码是否正确，正确返回0，错误返回2
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
	
	//查询余额
    public static float executeBalance(String cardID) {
        conn = getConn();
        float balance=0; //0为成功，1为查无此卡，2为密码错误
        String sql = "SELECT balance FROM cardinfo WHERE cardID=?";
        try {
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setString(1,cardID);
            ResultSet rs=pStatement.executeQuery();
            if (rs.next()) {
                float myBalance  = rs.getFloat("balance");
                balance = myBalance;
            }
            else {
                balance = -1;
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return balance;
    }

    //存款或取款操作，由operateState决定
    public static float executeOperate(String cardID,float amount,boolean operateState) {
        conn = getConn();
        float balance = -1; //默认为-1
        String sql = "SELECT balance FROM cardinfo WHERE cardID=?";
        try {
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setString(1,cardID);
            ResultSet rs=pStatement.executeQuery();
            if (rs.next()) {
                float myBalance  = rs.getFloat("balance");
                if (operateState==true) {//存款
                    sql = "UPDATE cardinfo SET balance=? WHERE cardID=?";
                    pStatement = conn.prepareStatement(sql);
                    pStatement.setFloat(1,(myBalance+amount));
                    pStatement.setString(2,cardID);
                    if ((pStatement.executeUpdate())!=0) {//如果成功UPDATE
                        balance = myBalance+amount;
                        sql = "INSERT INTO transinfo(transdate,cardID,transtype,transmoney) VALUES(?,?,?,?)";
                        pStatement = conn.prepareStatement(sql);
                        Date transdate = new Date();    
        				java.sql.Date date = new java.sql.Date(transdate.getTime());
                        pStatement.setDate(1,date);
                        pStatement.setString(2,cardID);
                        pStatement.setString(3,"存款");
                        pStatement.setFloat(4,amount);
                        pStatement.executeUpdate();
                    }
                }
                else {  //取款
                    if (myBalance >= amount) {
                        sql = "UPDATE cardinfo SET balance=? WHERE cardID=?";
                        pStatement = conn.prepareStatement(sql);
                        pStatement.setFloat(1,(myBalance-amount));
                        pStatement.setString(2,cardID);
                        if (pStatement.executeUpdate() != 0) {//如果成功UPDATE
                            balance=myBalance-amount;
                            sql = "INSERT INTO transinfo(transdate,cardID,transtype,transmoney) VALUES(?,?,?,?)";
                            pStatement = conn.prepareStatement(sql);
                            Date transdate = new Date();    
            				java.sql.Date date= new java.sql.Date(transdate.getTime());
                            pStatement.setDate(1,date);
                            pStatement.setString(2,cardID);
                            pStatement.setString(3,"取款");
                            pStatement.setFloat(4,amount);
                            pStatement.executeUpdate();
                        }
                    }
                    else {
                        balance = -1;//取款数目比余额多，失败返回-1
                    }
                }
            }
            else {
                balance = -1;//若余额不存在
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
        return balance;
    }
    
    public static float executeTrans(String cardID,String otherCardID, float amount) {
        conn = getConn();
        float balance = -1;//默认为-1
        String sql = "SELECT cardID FROM cardinfo WHERE cardID=?"; //验证对方卡号是否合法
        try {
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setString(1,otherCardID);
            ResultSet rs=pStatement.executeQuery();
            if (rs.next()) {
            	String sql1 = "SELECT balance FROM cardinfo WHERE cardID=?";
                pStatement = conn.prepareStatement(sql1);
                pStatement.setString(1,cardID);
                ResultSet rs1 = pStatement.executeQuery();
                
                String sql2 = "SELECT balance FROM cardinfo WHERE cardID=?";
                pStatement = conn.prepareStatement(sql2);
                pStatement.setString(1,otherCardID);
                ResultSet rs2 = pStatement.executeQuery();
                if (rs1.next() && rs2.next()) {
                    float myBalance = rs1.getFloat("balance");
                    float otherBalance = rs2.getFloat("balance");
                    if (myBalance >= amount) {
                    	balance = myBalance - amount;
                        sql = "UPDATE cardinfo SET balance=? WHERE cardID=?";
                        pStatement = conn.prepareStatement(sql);
                        pStatement.setFloat(1,balance);
                        pStatement.setString(2,cardID);
                        pStatement.addBatch();
                        pStatement.setFloat(1,(otherBalance+amount));
                        pStatement.setString(2,otherCardID);
                        pStatement.addBatch();
                        pStatement.executeBatch();

                        sql = "INSERT INTO transinfo(transdate,cardID,otherCardID,transtype,transmoney) VALUES(?,?,?,?,?) ";
                        pStatement = conn.prepareStatement(sql);
                        Date transdate = new Date();    
        				java.sql.Date date = new java.sql.Date(transdate.getTime());
                        pStatement.setDate(1,date);
                        pStatement.setString(2,cardID);
                        pStatement.setString(3,otherCardID);
                        pStatement.setString(4,"转账");
                        pStatement.setFloat(5,amount);
                        pStatement.executeUpdate();
                    }
                    else {
                        balance = -1; //余额不足
                    }
                }
                rs.close();
            }
            else {
                balance = -2;//对方卡号不存在
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
        return balance;
    }
    
    //根据卡号返回用户ID
    public static int executeGetCustomerID(String cardID) {
        conn = getConn();
        int customerID = 0;
        String sql = "SELECT customerID FROM cardinfo WHERE cardID=?";
        try {
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setString(1,cardID);
            ResultSet rs=pStatement.executeQuery();
            if (rs.next()) {
                customerID = rs.getInt("customerID");
            }
            else{
                customerID = -1;
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
        return customerID;
    }

    //查询用户是否挂失
    public static boolean executeIsLost(String cardID) {
        conn = getConn();
        String isLost = "否";
        String sql = "SELECT IsReportLoss FROM cardinfo WHERE cardID=?";
        try {
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setString(1,cardID);
            ResultSet rs=pStatement.executeQuery();
            if (rs.next()) {
                isLost = rs.getString("IsReportLoss");
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
        if (isLost.equals("是")) return true;
        return false;
    }
    
    //修改用户挂失状态
    public static void executeSetLost(String cardID,boolean state) {
        conn = getConn();
        String sql = "UPDATE cardinfo SET IsReportLoss=? WHERE cardID=?";
        try {
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setString(1,state?"是":"否");
            pStatement.setString(2,cardID);
            pStatement.executeUpdate();

        } catch (SQLException e) {
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
    }
    
    //获取该用户密码
    public static String executeGetPass(String cardID) {
        conn = getConn();
        String myCardPass = "";
        String sql = "SELECT cardID FROM cardinfo WHERE cardID=?";
        try {
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setString(1,cardID);
            ResultSet rs = pStatement.executeQuery();
            if (rs.next()) {
                String cardIDForLogin  = rs.getString("cardID");
                sql = "SELECT pass FROM cardinfo WHERE cardID=?";
                pStatement = conn.prepareStatement(sql);
                pStatement.setString(1,cardID);
                rs=pStatement.executeQuery();
                if(rs.next()){
                    myCardPass = rs.getString("pass");
                }
                rs.close();
            }
            else {
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
        return myCardPass;
    }
    
    //重置该用户密码
    public static int executeResetPass(String cardID,String newPass) {
        conn = getConn();
        int state = 0;
        String sql = "SELECT cardID FROM cardinfo WHERE cardID=?";
        try {
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setString(1,cardID);
            ResultSet rs = pStatement.executeQuery();
            if (rs.next()) {
               sql = "UPDATE cardinfo SET pass=? WHERE cardID=?";
               pStatement = conn.prepareStatement(sql);
               pStatement.setString(1,newPass);
               pStatement.setString(2,cardID);
               pStatement.executeUpdate();
               state = 1;
            }
            else{
                //不存在卡号
                state = 0;
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
        return state; //1成功0失败
    }

    public static CardInfo executeGetAllCardInfo(String cardID) {
        conn = getConn();
        CardInfo result = null;
        String sql = "SELECT * FROM cardinfo WHERE cardID=?";
        try {
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setString(1,cardID);
            ResultSet rs = pStatement.executeQuery();
            if (rs.next()) {
                result = new CardInfo(rs.getString("cardID"),
                                      rs.getString("curType"),
                                      rs.getString("savingType"),
                                      rs.getDate("openDate"),
                                      rs.getFloat("openMoney"),
                                      rs.getFloat("balance"),
                                      rs.getString("pass"),
                                      rs.getString("isReportLoss").equals("是")?true:false,
                                      rs.getInt("customerID"));
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

    public static int isPossessAnotherCard(int customerID) {
        conn = getConn();
        int quantityOfCards = 0;
        String sql = "SELECT COUNT(*) FROM cardinfo WHERE customerID=?";
        //String sql="SELECT customerID,customerName,pID,telephone,address FROM Card_User WHERE cardID=?";
        try {
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setInt(1,customerID);
            ResultSet rs=pStatement.executeQuery();
            if (rs.next()) {
                quantityOfCards = rs.getInt(1);
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
        return quantityOfCards;
    }
}
