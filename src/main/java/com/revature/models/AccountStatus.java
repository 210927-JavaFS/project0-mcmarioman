package com.revature.models;

public class AccountStatus {
	private byte accountStatusId;
	private String accountStatusName;
	
	
	public AccountStatus() {
		super();
	}
			
	public AccountStatus(byte accountStatusId, String accountStatusName) {
		super();
		this.accountStatusId = accountStatusId;
		this.accountStatusName = accountStatusName;
	}
	public byte getAccountStatusId() {
		return accountStatusId;
	}
	public String getAccountStatusName() {
		return accountStatusName;
	}
	public void setAccountStatusId(byte accountStatusId) {
		this.accountStatusId = accountStatusId;
	}
	public void setAccountStatusName(String accountStatusName) {
		this.accountStatusName = accountStatusName;
	}
	
	
}
