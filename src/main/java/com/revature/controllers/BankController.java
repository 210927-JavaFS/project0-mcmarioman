package com.revature.controllers;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.Account;
import com.revature.models.Customer;
import com.revature.services.AccountService;
import com.revature.services.CustomerService;
import com.revature.utils.GlobalUtil;

public class BankController {
	
	protected Scanner scan = new Scanner(System.in);
	
	private static Logger log = LoggerFactory.getLogger(BankController.class);
	
	protected void loadFindCustomerUI() {
		System.out.println("\nCustomer search...");
		
		GlobalUtil.printColorMessage("Please type customer first or last name:", "cyan");
		
		String name = scan.nextLine();
		
		CustomerService cs = new CustomerService();
		AccountService as = new AccountService();
		
		List<Customer> list = cs.findCustomerByName(name);
		
		if(list != null) {
			System.out.println("\nSearch result: \n");
			for (Customer c: list) {
				log.info("User reviewing accounts info of: " + c.getUserName());
				System.out.println(c.toString());
				List<Account> account_list =  as.getAccountsByUserId(c.getUserId());
				
				for(Account a: account_list) {
					System.out.println(".");
					System.out.println("...." + a.toString());
				}
				System.out.println(".");
				System.out.println(".");
			}
			
		}else {
			log.info("User didn't find any matches");
			GlobalUtil.printColorMessage("No results match your search criteria.", "yellow");
		}
		
	}
	
	protected void loadViewAccountInfoUI() {
		System.out.println("\nAccount info...");
		GlobalUtil.printColorMessage("Please type the account number:", "cyan");
		int accountNumber = 0;
		try {
			accountNumber = Integer.parseInt(scan.nextLine());
		}catch(Exception e) {
			GlobalUtil.printColorMessage("Account number is not valid!", "red");
			return ;
		}
		log.info("User reviewing account info: " + accountNumber);
		AccountService as = new AccountService();
		
		Account account = as.getAccountByNumber(accountNumber);
		
		if(account != null) {			
			printAccountInfo(account);
		}else {
			log.info("Account does not exists: " + accountNumber);
			GlobalUtil.printColorMessage("Account was not found!", "yellow");
		}
	}
	
	protected void printAccountInfo(Account account){
		System.out.println("\nAccount info:\n");
		System.out.println(account.getUser().toString());
		System.out.println(".");
		System.out.println("...." + account.toString());
		System.out.println(".");
		System.out.println(".");
	}
	
	protected void loadApproveAccountUI() {
		System.out.println("\nShowing account requests...\n");
		
		AccountService as = new AccountService();
		
		List<Account> list = as.getAccountsByUserStatusId(1);
		
		if(list == null) {
			GlobalUtil.printColorMessage("There are not pending approval accounts.", "yellow");
			return;
		}
		
		if(list.size() == 0) {
			GlobalUtil.printColorMessage("There are not pending approval accounts.", "yellow");
			return;
		}
		
		for(Account c: list) {
			System.out.println(c.getUser().toString());
			System.out.println(".");
			System.out.println("...." + c.toString());
			System.out.println(".");
			System.out.println(".");
		}
		
		GlobalUtil.printColorMessage("Please type the account number you want to approve or deny:", "cyan");
		
		int accountNumber = 0;
		
		try {
			accountNumber = Integer.parseInt(scan.nextLine());
		}catch(Exception ex) {
			log.warn("User attemped to approve an invalid account number: " + accountNumber);
			GlobalUtil.printColorMessage("Account number is not valid!", "red");
			return;
		}
		
		if(!as.validateAccountByStatus(accountNumber, 1)) {
			log.warn("User attemped to approve an account already approved " + accountNumber);
			GlobalUtil.printColorMessage("Account is not on pending approval status!", "yellow");
			return;
		}
		
		System.out.println("\nWhat would you like to do?\n1 - Approve\n2 - Deny");
		
		char option = scan.nextLine().charAt(0);
		
		int accountStatusId = 0;
		
		switch(option ) {
		case '1':
			accountStatusId = 2;
			break;
		case '2':
			accountStatusId = 4;
			break;
			default:
				GlobalUtil.printColorMessage("That is not a valid option!", "yellow");
				return;
		}
		
		if(as.changeAccountStatus(accountNumber, accountStatusId)) {
			log.info("User approved account: " + accountNumber);
			GlobalUtil.printColorMessage("Account status was changed successfully!", "green");
		}else {
			log.info("User denied account: " + accountNumber);
			GlobalUtil.printColorMessage("We could not change the account status at this time. Please try again later.", "yellow");
		}
		
	}
}
