package com.revature.models;

public class Customer extends User{

	public Customer(int Userid, String userName, byte UserRoleId) {
		super(Userid, userName, UserRoleId);
		// TODO Auto-generated constructor stub
	}
	public Customer(String userName, String password, String firstName, String lastName, String email, String phoneNumber) {
		super(userName, password, firstName, lastName);
		this.email = email;
		this.phoneNumber = phoneNumber;
	}
	private String email;
	private String phoneNumber;
	
	public String getEmail() {
		return email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	@Override
	public String toString() {
		return super.toString()  + " - Email= " + email + " - Phone Number= " + phoneNumber;
	}
	
	
	
	
}
