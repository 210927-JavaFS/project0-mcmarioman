package com.revature.daos;

import java.util.List;

import com.revature.models.Account;
import com.revature.models.User;


public interface AccountDAO {
	List<Account> getAccountsByUserId(int UserId);
	List<Account> getAccountsByUserStatusId(int AccountStatusId);
	Account  getAccountByNumber(int account_number);
	boolean updateAccountBalance(Account account);
	boolean createNewAccount(Account account);
	Account validateAccount(int account, User user);
	boolean changeAccountStatus(int accountNumber, int accountStatusId);
	boolean transferFunds(Account accountFrom, Account accountTo, double amount);
}
