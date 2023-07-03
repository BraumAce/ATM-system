package model;

import java.sql.Date;

public class CardInfo {
	private String cardID,curType,savingType;;
	private Date openDate;
	private float openMoney,balance;
	private String pass;
	private boolean IsReportLoss;
	private int customerID;
	
	public CardInfo(String cardID, String curType, String savingType, Date openDate, float openMoney, float balance,
					String pass, boolean isReportLoss, int customerID) {
		super();
		this.cardID = cardID;
		this.curType = curType;
		this.savingType = savingType;
		this.openDate = openDate;
		this.openMoney = openMoney;
		this.balance = balance;
		this.pass = pass;
		IsReportLoss = isReportLoss;
		this.customerID = customerID;
	}

	public String getCardID() {
		return cardID;
	}
	public void setCardID(String cardID) {
		this.cardID = cardID;
	}
	public String getCurType() {
		return curType;
	}
	public void setCurType(String curType) {
		this.curType = curType;
	}
	public String getSavingType() {
		return savingType;
	}
	public void setSavingType(String savingType) {
		this.savingType = savingType;
	}
	public Date getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	public float getOpenMoney() {
		return openMoney;
	}
	public void setOpenMoney(float openMoney) {
		this.openMoney = openMoney;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public boolean isIsReportLoss() {
		return IsReportLoss;
	}
	public void setIsReportLoss(boolean isReportLoss) {
		IsReportLoss = isReportLoss;
	}
	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
}
