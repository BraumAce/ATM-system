package model;

public class UserInfo {
	private int customerID;
	private String customerName,PID,telephone,address;
	
	public UserInfo(int customerID, String customerName, String pID, String telephone, String address) {
		super();
		this.customerID = customerID;
		this.customerName = customerName;
		PID = pID;
		this.telephone = telephone;
		this.address = address;
	}

	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getPID() {
		return PID;
	}
	public void setPID(String pID) {
		PID = pID;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
