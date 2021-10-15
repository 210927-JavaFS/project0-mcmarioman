package com.revature.services;

import java.util.List;

import com.revature.daos.AccountDAO;
import com.revature.daos.AccountDAOImpl;
import com.revature.models.Account;
import com.revature.models.AccountType;
import com.revature.models.User;
public class AccountService {

	private AccountDAO accountDAO = new AccountDAOImpl();
	
	public boolean depositFunds(Account account, double amount) {
		//Call account DAO to update account
		
		account.setBalance(account.getBalance()  + amount);
		
		return accountDAO.updateAccountBalance(account);
	}
	
	public Account validateAccount(int account, User user) {
		return accountDAO.validateAccount(account, user);
	}
	
	public boolean withdrawFunds(Account account, double amount) {
		//Call account DAO to update account
		
		if(account.getBalance() < amount) {
			return false;
		}
		
		account.setBalance(account.getBalance() - amount);
		return accountDAO.updateAccountBalance(account);
	}
	
	public boolean createNewAccount(User user, AccountType accountType) {
		
		Account account = new Account();
		account.setUser(user);
		account.setAccountType(accountType);
		
		return accountDAO.createNewAccount(account);
		
	}
	
	public List<Account> getAccountsByUserId(int userId){
		return accountDAO.getAccountsByUserId(userId);
	}
	
	public List<Account> getAccountsByUserStatusId (int AccountStatusId){
		return accountDAO.getAccountsByUserStatusId(AccountStatusId);
	}
	
	public boolean validateAccountByStatus(int accountNumber, int accountStatusId) {
		Account account = accountDAO.getAccountByNumber(accountNumber);
		
		if(account == null) {
			return false;
		}else {
			
			if(account.getAccountStatus().getAccountStatusId() == accountStatusId) {
				return true;
			}
			
		}
		return false;
	}
	
	public boolean changeAccountStatus(int accountNumber, int accountStatusId) {
		return accountDAO.changeAccountStatus(accountNumber, accountStatusId);
	}
	
	public Account getAccountByNumber(int accountNumber) {
		
		return accountDAO.getAccountByNumber(accountNumber);
	}
	
	public boolean transferFunds(Account accountFrom, Account accountTo, double amount) {
		//Previously to transfer funds the accounts were validated
		
		if(accountFrom.getBalance() < amount) {
			return false;
		}
		
		return accountDAO.transferFunds(accountFrom, accountTo, amount);
	}
	
}
