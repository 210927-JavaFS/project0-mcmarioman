package com.revature.models;

public class User {
	
	public User(int Userid, String userName, byte UserRoleId) {
		super();
		this.userId = Userid;
		this.userName = userName;
		this.UserRoleId = UserRoleId;
	}
	
	public User(String userName, String password, String firstName, String lastName) {
		super();
		this.userName = userName;
		this.password = password;
		this.first_name = firstName;
		this.last_name = lastName;
	}
	
	private int userId;
	private String userName;
	private String password;
	private byte UserRoleId;
	
	@Override
	public String toString() {
		//Id: " + userId + " -
		return "User Name: " + userName + " - First Name: " + first_name + " - Last Name: " + last_name;
	}

	private String first_name;
	private String last_name;
	
	public String getFirst_name() {
		return first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public String getPassword() {
		return password;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public int getUserId() {
		return userId;
	}
	public String getUserName() {
		return userName;
	}
	public byte getUserRoleId() {
		return UserRoleId;
	}
	

	
}
