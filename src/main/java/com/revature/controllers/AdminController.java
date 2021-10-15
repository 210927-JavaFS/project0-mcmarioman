package com.revature.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.Account;
import com.revature.models.User;
import com.revature.services.AccountService;
import com.revature.utils.GlobalUtil;

public class AdminController extends BankController {
	
	
	private static Logger log = LoggerFactory.getLogger(AdminController.class);
	
	public  void showAdminMenu(User admin) {
		GlobalUtil.printColorMessage("You are logged in as " + admin.getUserName() + "\nWhat do you want to do today?", "cyan");
		boolean done = false;
		
		String option = "";
		
		
		/*Validate user input*/
		while(!done) {
			
			boolean isValid = true;
			
			System.out.println("1 - Customer search.");
			System.out.println("2 - View account information.");
			System.out.println("3 - Deposit funds to an account.");
			System.out.println("4 - Withdraw funds from an account.");
			System.out.println("5 - Transfer funds between accounts.");
			System.out.println("6 - View account requests.");
			System.out.println("7 - Cancel an account.");
			System.out.println("8 - Log out.");
			
			option = scan.nextLine();
			
			switch (option) {
			case "1":
				log.info("In to Customer search");
				loadFindCustomerUI();
				break;
			case "2":
				log.info("In to View account information");
				loadViewAccountInfoUI();
				break;
			case "3":
				log.info("In to Deposit funds to an account");
				loadDepositUI(admin);
				break;
			case "4":
				log.info("In to Withdraw funds from an account");
				loadWithdrawUI(admin);
				break;
			case "5":
				log.info("In to Transfer funds between accounts");
				loadTransferUI(admin);
				break;
			case "6":
				log.info("In to View account requests");
				loadApproveAccountUI();
				break;
			case "7":
				log.info("In to Cancel an account");
				loadCancelAccountUI(admin);
				break;
			case "8":
				done =true;
				break;
			default:
				GlobalUtil.printColorMessage("Please choose a valid option.", "yellow");
					isValid = false;
					break;
			}
			if(!option.equals("8") && isValid) {
				GlobalUtil.waitForEnterKey(scan);
			}
		}
		
		System.out.println("You have been logged out. Have a nice day!");
		log.info("Session ends.");
	}
	
	public void loadWithdrawUI(User admin) {
		System.out.println("\nWithdrawing funds...");

		System.out.println("\nType account number: ");
		
		Account account = this.validateUserAccountInput(admin);
		
		if(account == null) {
			return;
		}
		
		AccountService as = new AccountService();
		
		printAccountInfo(account);
		System.out.println("How much do you want to withdraw?: ");
		
		double amount = GlobalUtil.validateUserAmountInput(scan);
		
		if(amount == 0) {
			log.warn("User typed invalid deposit amount");
			return;
		}
		//Think about validating transaction
		
		if(as.withdrawFunds(account, amount)) {
			log.info("User withdrew " + amount + " from account " + account.getAccountNumber());
			GlobalUtil.printColorMessage("The transaction is complete! Account " + account.getAccountNumber() + " was debited $" + amount + ".", "green");
		}else {
			log.warn("Withdras failed not enough funds, amount: " + amount + ",  account: " + account.getAccountNumber());
			GlobalUtil.printColorMessage("Account does not have enough funds to complete this transaction.", "yellow");
		}
		
	}
	
	public void loadDepositUI(User admin) {
		System.out.println("\nDepositing funds...");

		System.out.println("\nType account number: ");
		
		Account account = this.validateUserAccountInput(admin);
		
		if(account == null) {
			return;
		}
		
		AccountService as = new AccountService();

		printAccountInfo(account);
		
		System.out.println("What's the deposit amount?: ");
		
		double amount = GlobalUtil.validateUserAmountInput(scan);
		
		if(amount == 0) {
			log.warn("User typed invalid deposit amount");
			return;
		}		
		
		if(as.depositFunds(account, amount)) {
			log.info("User credited " + amount + " from account " + account.getAccountNumber());
			GlobalUtil.printColorMessage("the deposit was successfully completed. Account " + account.getAccountNumber() + " was credit with $" + amount + ".", "green");
		}else {
			log.warn("Deposit failed, amount: " + amount + ",  account: " + account.getAccountNumber());
			GlobalUtil.printColorMessage("The transaction cannot be completed. Please try again later.", "yellow");
		}

		
	}
	
	public Account validateUserAccountInput(User admin) {
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
		Account account = as.validateAccount(accountNumber, admin);
		if(account == null) {
			log.warn("Account inactive or invalid!");
			GlobalUtil.printColorMessage("Account number is not valid!", "yellow");
			return null;
		}
		return account;
		
	}
	
	public void loadTransferUI(User admin) {
		System.out.println("\nTransfering funds...");
		
		System.out.println("\nTransfer From. Type account number: ");
		
		Account accountFrom = validateUserAccountInput(admin);
		
		if(accountFrom == null) {
			return;
		}
		
		AccountService as = new AccountService();

		printAccountInfo(accountFrom);
		
		System.out.println("\nTransfer To. Type account number: ");
		Account accountTo = validateUserAccountInput(admin);
		
		if(accountTo == null) {
			return;
		}

		printAccountInfo(accountTo);
		
		if(accountFrom.getAccountNumber() == accountTo.getAccountNumber()) {
			log.warn("User attempted to transfer money to the same account.");
			GlobalUtil.printColorMessage("You can only transfer between two different accounts. ", "yellow");
			return;
		}
		
		System.out.println("Please type the transfer amount: ");
		
		double amount = GlobalUtil.validateUserAmountInput(scan);
		
		if(amount == 0) {
			return;
		}
		
		
		if(as.transferFunds(accountFrom, accountTo, amount)) {
			log.info("User transfered " + amount + " from " + accountFrom.getAccountNumber() + " to " + accountTo.getAccountNumber());
			GlobalUtil.printColorMessage("The transfer was completed successfully.", "green");
		}else {
			log.warn("Transfer failed, amount: " + amount + " from " + accountFrom.getAccountNumber() + " to " + accountTo.getAccountNumber());
			GlobalUtil.printColorMessage("The transaction cannot be completed. Please try again later.", "yellow");
		}
		
	}
	
	private void loadCancelAccountUI(User admin) {
		System.out.println("\nCanceling account...");
		
		System.out.println("\nType account number: ");
		
		Account account = validateUserAccountInput(admin);
		
		if(account == null) {
			return;
		}
		
		AccountService as = new AccountService();
		printAccountInfo(account);
		
		if(account.getBalance() > 0) {
			GlobalUtil.printColorMessage("We can't cancel the account because it has a positive balance.", "yellow");
			log.warn("Canceling account failed, account " + account.getAccountNumber() + " still has postive balance");
			return;
		}
		
		
		System.out.println("\nAre you sure do you want to cancel this account? y/n");
		
		char answer = scan.nextLine().charAt(0);
		
		if(answer == 'y') {
			if(as.changeAccountStatus(account.getAccountNumber(), 3)) {
				log.info("User canceled account " + account.getAccountNumber());
				GlobalUtil.printColorMessage("Account " + account.getAccountNumber() + " was canceled.", "green");
			}else {
				log.warn("Canceling account failed, account " + account.getAccountNumber());
				GlobalUtil.printColorMessage("Account was not canceled. Please try again later.", "yellow");
			}
		}else {
			System.out.println("Account will not be canceled.");
		}
	}
}
