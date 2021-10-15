package com.revature.models;

import java.text.NumberFormat;

import com.revature.utils.GlobalUtil;

public class Account {
	public Account() {
		super();
	}
	public Account(int accountNumber, AccountType accountType, User user, double balance, AccountStatus accountStatus,
			String dateOpened) {
		super();
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.user = user;
		this.balance = balance;
		this.accountStatus = accountStatus;
		this.dateOpened = dateOpened;
	}
	private int accountNumber;
	private AccountType accountType;
	private User user;
	private double balance;
	private AccountStatus accountStatus;
	private String dateOpened;
	private String dateApproved;
	
	public int getAccountNumber() {
		return accountNumber;
	}
	public AccountType getAccountType() {
		return accountType;
	}
	public double getBalance() {
		return balance;
	}
	public AccountStatus getAccountStatus() {
		return accountStatus;
	}
	public String getDateOpened() {
		return dateOpened;
	}
	public String getDateApproved() {
		return dateApproved;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public void setApproved(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}
	public void setDateOpened(String dateOpened) {
		this.dateOpened = dateOpened;
	}
	public void setDateApproved(String dateApproved) {
		this.dateApproved = dateApproved;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}
	@Override
	public String toString() {
		
		String color = "";
		
		switch(this.accountStatus.getAccountStatusId()) {
		case 1:
			color = "yellow";
			break;
		case 2:
			color = "green";
			break;
		case 3: case 4:
			color = "red";
			break;
		}
		
		
		StringBuilder sb = new StringBuilder("");
		sb.append("Account number: ");
		sb.append(accountNumber);
		sb.append(" - Account type: ");
		sb.append(accountType.getAccountTypeName());
		sb.append(" - Status: ");
		sb.append(GlobalUtil.addColorToString(accountStatus.getAccountStatusName(), color));
		sb.append(" - Balance: ");
		//
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		String sbalance = formatter.format(balance);
		//
		sb.append(sbalance);
		
		return sb.toString();
	}
}
