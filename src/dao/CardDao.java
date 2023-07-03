package dao;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.CardInfo;

public class CardDao extends BaseDao {

	static Connection conn;
	
	//���CardInfo
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
			pStatement.setString(8, card.isIsReportLoss()?"��":"��");
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
	
	//���ݿ�����ɾ����CardInfo
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

	//�жϵ�¼�û����������Ƿ���ȷ
	public static int executeLogin(String cardID,String pass) {
		conn = getConn();
		int state = 1; //0Ϊ�ɹ���1Ϊ���޴˿���2Ϊ�������
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
            //��֤�Ƿ���ڸÿ�������stateΪ0��������Ϊ1
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
                //��֤�����Ƿ���ȷ����ȷ����0�����󷵻�2
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
	
	//��ѯ���
    public static float executeBalance(String cardID) {
        conn = getConn();
        float balance=0; //0Ϊ�ɹ���1Ϊ���޴˿���2Ϊ�������
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
            //��֤�Ƿ���ڸÿ�������stateΪ0��������Ϊ1

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

    //����ȡ���������operateState����
    public static float executeOperate(String cardID,float amount,boolean operateState) {
        conn = getConn();
        float balance = -1; //Ĭ��Ϊ-1
        String sql = "SELECT balance FROM cardinfo WHERE cardID=?";
        try {
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setString(1,cardID);
            ResultSet rs=pStatement.executeQuery();
            if (rs.next()) {
                float myBalance  = rs.getFloat("balance");
                if (operateState==true) {//���
                    sql = "UPDATE cardinfo SET balance=? WHERE cardID=?";
                    pStatement = conn.prepareStatement(sql);
                    pStatement.setFloat(1,(myBalance+amount));
                    pStatement.setString(2,cardID);
                    if ((pStatement.executeUpdate())!=0) {//����ɹ�UPDATE
                        balance = myBalance+amount;
                        sql = "INSERT INTO transinfo(transdate,cardID,transtype,transmoney) VALUES(?,?,?,?)";
                        pStatement = conn.prepareStatement(sql);
                        Date transdate = new Date();    
        				java.sql.Date date = new java.sql.Date(transdate.getTime());
                        pStatement.setDate(1,date);
                        pStatement.setString(2,cardID);
                        pStatement.setString(3,"���");
                        pStatement.setFloat(4,amount);
                        pStatement.executeUpdate();
                    }
                }
                else {  //ȡ��
                    if (myBalance >= amount) {
                        sql = "UPDATE cardinfo SET balance=? WHERE cardID=?";
                        pStatement = conn.prepareStatement(sql);
                        pStatement.setFloat(1,(myBalance-amount));
                        pStatement.setString(2,cardID);
                        if (pStatement.executeUpdate() != 0) {//����ɹ�UPDATE
                            balance=myBalance-amount;
                            sql = "INSERT INTO transinfo(transdate,cardID,transtype,transmoney) VALUES(?,?,?,?)";
                            pStatement = conn.prepareStatement(sql);
                            Date transdate = new Date();    
            				java.sql.Date date= new java.sql.Date(transdate.getTime());
                            pStatement.setDate(1,date);
                            pStatement.setString(2,cardID);
                            pStatement.setString(3,"ȡ��");
                            pStatement.setFloat(4,amount);
                            pStatement.executeUpdate();
                        }
                    }
                    else {
                        balance = -1;//ȡ����Ŀ�����࣬ʧ�ܷ���-1
                    }
                }
            }
            else {
                balance = -1;//��������
            }
            rs.close();
            //��֤�Ƿ���ڸÿ�������stateΪ0��������Ϊ1
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
        float balance = -1;//Ĭ��Ϊ-1
        String sql = "SELECT cardID FROM cardinfo WHERE cardID=?"; //��֤�Է������Ƿ�Ϸ�
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
                        pStatement.setString(4,"ת��");
                        pStatement.setFloat(5,amount);
                        pStatement.executeUpdate();
                    }
                    else {
                        balance = -1; //����
                    }
                }
                rs.close();
            }
            else {
                balance = -2;//�Է����Ų�����
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
    
    //���ݿ��ŷ����û�ID
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
            //��֤�Ƿ���ڸÿ�������stateΪ0��������Ϊ1
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

    //��ѯ�û��Ƿ��ʧ
    public static boolean executeIsLost(String cardID) {
        conn = getConn();
        String isLost = "��";
        String sql = "SELECT IsReportLoss FROM cardinfo WHERE cardID=?";
        try {
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setString(1,cardID);
            ResultSet rs=pStatement.executeQuery();
            if (rs.next()) {
                isLost = rs.getString("IsReportLoss");
            }
            rs.close();
            //��֤�Ƿ���ڸÿ�������stateΪ0��������Ϊ1
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
        if (isLost.equals("��")) return true;
        return false;
    }
    
    //�޸��û���ʧ״̬
    public static void executeSetLost(String cardID,boolean state) {
        conn = getConn();
        String sql = "UPDATE cardinfo SET IsReportLoss=? WHERE cardID=?";
        try {
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setString(1,state?"��":"��");
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
    
    //��ȡ���û�����
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
            //��֤�Ƿ���ڸÿ�������stateΪ0��������Ϊ1
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
    
    //���ø��û�����
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
                //�����ڿ���
                state = 0;
            }
            rs.close();
            //��֤�Ƿ���ڸÿ�������stateΪ0��������Ϊ1
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
        return state; //1�ɹ�0ʧ��
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
                                      rs.getString("isReportLoss").equals("��")?true:false,
                                      rs.getInt("customerID"));
            }
            rs.close();
            //��֤�Ƿ���ڸÿ�������stateΪ0��������Ϊ1
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
            //��֤�Ƿ���ڸÿ�������stateΪ0��������Ϊ1
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
