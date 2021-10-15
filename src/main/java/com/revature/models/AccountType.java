package com.revature.models;

public class AccountType {
	private byte accountTypeid;
	private String accountTypeName;
	
	public AccountType() {
		super();
	}
	public AccountType(byte accountTypeid, String accountTypeName) {
		super();
		this.accountTypeid = accountTypeid;
		this.accountTypeName = accountTypeName;
	}
	public byte getAccountTypeId() {
		return accountTypeid;
	}
	public String getAccountTypeName() {
		return accountTypeName;
	}

	public void setAccountTypeId(byte accountTypeid) {
		this.accountTypeid = accountTypeid;
	}
	public void setAccountTypeName(String accountTypeName) {
		this.accountTypeName = accountTypeName;
	}
	
}
