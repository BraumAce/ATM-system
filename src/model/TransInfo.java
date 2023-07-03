package model;

import java.sql.Date;

public class TransInfo {
	private Date transDate;
	private String cardID,otherCardID,transType;
	private float transMoney;
	private String remark;

	public TransInfo(Date transDate, String cardID, String otherCardID,
			String transType, float transMoney, String remark) {
		super();
		this.transDate = transDate;
		this.cardID = cardID;
		this.otherCardID = otherCardID;
		this.transType = transType;
		this.transMoney = transMoney;
		this.remark = remark;
		
	}
	
	public Date getTransDate() {
		return transDate;
	}
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}
	public String getCardID() {
		return cardID;
	}
	public void setCardID(String cardID) {
		this.cardID = cardID;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public float getTransMoney() {
		return transMoney;
	}
	public void setTransMoney(float transMoney) {
		this.transMoney = transMoney;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOtherCardID() {
		return otherCardID;
	}
	public void setOtherCardID(String otherCardID) {
		this.otherCardID = otherCardID;
	}
}
