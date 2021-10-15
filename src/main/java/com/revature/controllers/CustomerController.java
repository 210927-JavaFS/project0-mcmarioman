package com.revature.controllers;
import com.revature.models.Account;
import com.revature.models.AccountType;
import com.revature.models.User;
import com.revature.services.AccountService;

import com.revature.services.AccountTypeService;
import com.revature.utils.GlobalUtil;

import jdk.internal.org.jline.utils.Log;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerController {
	
	private Scanner scan = new Scanner(System.in);
	private static Logger log = LoggerFactory.getLogger(CustomerController.class);
	
	public void showCustomerMenu(User customer) {
	
		
		GlobalUtil.printColorMessage("You are logged in as " + customer.getUserName() + "\nHow can we help you today?", "cyan");
		//System.out.println("How can we help you today?");
		
		boolean done = false;
		
		String option = "";
		
		
		/*Validate user input*/
		while(!done) {
			
			boolean isValid = true;
			
			System.out.println("1 - Check my account balance.");
			System.out.println("2 - Deposit funds to my account.");
			System.out.println("3 - Withdraw funds from my account");
			System.out.println("4 - Transfer funds");
			System.out.println("5 - Apply for a new account");
			System.out.println("6 - Log out ");
			
			option = scan.nextLine();			
			
			switch (option) {
				case "1":
					log.info("In to check account balance");
					showUserAccounts(customer);
					break;
				case "2":
					log.info("In to deposit funds.");
					loadDepositUI(customer);
					break;
				case "3":
					log.info("In to withdraw funds");
					loadWithdrawUI(customer);
					break;
				case "4":
					log.info("In to transfer funds");
					loadTransferUI(customer);
					break;
				case "5":
					log.info("In to apply to a new account");
					loadApplyForNewAccountUI(customer);
					break;
				case "6":
					done =true;
					break;
				default:
					GlobalUtil.printColorMessage("Please choose a valid option.", "yellow");
						isValid = false;
						break;
			}
			//logging out
			if(!option.equals("6") && isValid) {
				GlobalUtil.waitForEnterKey(scan);
			}
			
		}
		
		System.out.println("You have been logged out. Have a nice day!");
		log.info("Session ends.");
	}
	public void showUserAccounts(User customer) {
		
		GlobalUtil.printColorMessage("Your accounts:", "cyan");
		
		AccountService as = new AccountService();
		
		List<Account> list = as.getAccountsByUserId(customer.getUserId());
		
		if(list.size() == 0) {
			GlobalUtil.printColorMessage("You currently don't have any accounts.", "yellow");
			return;
		}
		
		for (Account c: list) {
			
			System.out.println(c.toString());			
		}
		
	}
	
	public void loadApplyForNewAccountUI(User customer) {
		System.out.println("\nApplying for a new account...");
		
		GlobalUtil.printColorMessage("What type of account do you want to open?", "cyan");
		
		AccountTypeService ats = new AccountTypeService();
		
		for(AccountType at : ats.getAccountTypes()) {
			
			StringBuilder sb = new StringBuilder();
			sb.append(at.getAccountTypeId());
			sb.append(" - ");
			sb.append(at.getAccountTypeName());
			System.out.println(sb.toString());
		}
		
		
		byte accountTypeId = 0;
		
		try {
			accountTypeId = Byte.parseByte(scan.nextLine());
			
		}catch(Exception ex) {
			GlobalUtil.printColorMessage("Invalid account type.", "red");
			return;
		}
		
		if(accountTypeId == 1 || accountTypeId == 2) {
			
			AccountType accountType = ats.getAccountById(accountTypeId);
			
			StringBuilder sb = new StringBuilder("You are applying for a new ");
			sb.append(accountType.getAccountTypeName());
			sb.append(" Account. Please confirm y/n.");
			
			System.out.println(sb.toString());
			
			char confirm = scan.nextLine().charAt(0);
			
			if(confirm == 'y') {
				AccountService as = new AccountService();
				if(as.createNewAccount(customer, accountType)) {
					log.info("User applied to a new " + accountType.getAccountTypeName() + " account");
					GlobalUtil.printColorMessage("Your application was submitted successfully!\nWe will let you know once is approved.", "green");
				}else {
					log.warn("Applying to a new Account failed.");
					GlobalUtil.printColorMessage("There was a problem with your application. Please try again later.", "red");
				}
				
				
			}else {
				System.out.println("Your application was canceled.");
			}
			
			
		}else {
			log.warn("Customer attempted to apply to an invalid account type.");
			GlobalUtil.printColorMessage("Invalid account type.", "red");
			return;
		}
	}
	
	public void loadTransferUI(User customer) {
		System.out.println("\nTransfering funds...");
		showUserAccounts(customer);
		System.out.println("\nTransfer From. Type account number: ");
		
		Account accountFrom = this.validateUserAccountInput(customer);
		
		if(accountFrom == null) {
			return;
		}
		
		System.out.println("\nTransfer To. Type account number: ");
		Account accountTo = this.validateUserAccountInput(customer);
		
		if(accountTo == null) {
			return;
		}
		
		if(accountFrom.getAccountNumber() == accountTo.getAccountNumber()) {
			log.warn("User attempted to transfer money to the same account.");
			GlobalUtil.printColorMessage("You can only transfer between two different accounts. ", "yellow");
			return;
		}
		
		System.out.println("How much do you want to transfer?: ");
		
		double amount = GlobalUtil.validateUserAmountInput(scan);
		
		if(amount == 0) {
			return;
		}
		//This method needs to confirm transaction
		AccountService as = new AccountService();
		if(as.transferFunds(accountFrom, accountTo, amount)) {
			log.info("User transfered " + amount + " from " + accountFrom + " to " + accountTo);
			GlobalUtil.printColorMessage("The transfer was completed successfully.", "green");
		}else {
			log.warn("Transfer failed, amount: " + amount + " from " + accountFrom + " to " + accountTo);
			GlobalUtil.printColorMessage("Account " + accountFrom.getAccountNumber() + " does not have enough funds to complete this transaction.", "yellow");
		}
		
	}
	
	public void loadDepositUI(User customer) {
		System.out.println("\nDepositing funds...");
		showUserAccounts(customer);
		System.out.println("\nType account number: ");
		
		Account account = this.validateUserAccountInput(customer);
		
		if(account == null) {
			return;
		}
		
		System.out.println("What's the deposit amount?: ");
		
		double amount = GlobalUtil.validateUserAmountInput(scan);
		
		if(amount == 0) {
			log.warn("User typed invalid deposit amount");
			return;
		}
				
		//Think about validating transaction
		AccountService as = new AccountService();
		
		if(as.depositFunds(account, amount)) {
			GlobalUtil.printColorMessage("Your deposit was successfully completed.", "green");
			GlobalUtil.printColorMessage("the deposit was successfully completed. Account " + account.getAccountNumber() + " was credit with $" + amount + ".", "green");
		}else {
			log.warn("Deposit failed, amount: " + amount + ",  account: " + account.getAccountNumber());
			GlobalUtil.printColorMessage("Your transaction cannot be completed. Please try again later.", "yellow");
		}

		
	}
	
	public void loadWithdrawUI(User customer) {
		System.out.println("\nWithdrawing funds...");
		showUserAccounts(customer);
		System.out.println("\nType account number: ");
		
		Account account = validateUserAccountInput(customer);
		
		if(account == null) {
			return;
		}
		
		System.out.println("How much do you want to withdraw?: ");
		
		double amount = GlobalUtil.validateUserAmountInput(scan);
		
		if(amount == 0) {
			log.warn("User typed invalid withdrawal amount.");
			return;
		}
		//Think about validating transaction
		AccountService as = new AccountService();
		if(as.withdrawFunds(account, amount)) {
			GlobalUtil.printColorMessage("Your transaction is complete! Please take your cash.", "green");
			log.info("User withdrew " + amount + " from account " + account.getAccountNumber());
		}else {
			GlobalUtil.printColorMessage("You don't have enough funds to complete this transaction.", "yellow");
			log.warn("Withdras failed not enough funds, amount: " + amount + ",  account: " + account.getAccountNumber());
		}
		
	}
	
	public Account validateUserAccountInput(User customer) {
		int accountNumber = 0;
		try {
			accountNumber = Integer.parseInt(scan.nextLine());
		}catch(Exception e) {
			log.warn("Invalid account number");
			GlobalUtil.printColorMessage("Account number is not valid!", "red");
			return null;
		}
		/*calling account service to validate account*/
		AccountService as = new AccountService();
		//Validates that account belongs to user or user is an Administrator
		
		Account account = as.validateAccount(accountNumber, customer);
		
		if(account == null) {
			log.warn("Account inactive or invalid!");
			GlobalUtil.printColorMessage("Account number is not valid!", "yellow");
			return null;
		}
		return account;
		
	}
	
	
	

}
