package com.revature.services;

import java.util.List;

import com.revature.daos.AccountTypeDAO;
import com.revature.daos.AccountTypeDAOImpl;
import com.revature.models.AccountType;

public class AccountTypeService {
	
	
	private AccountTypeDAO accountTypeDAO = new AccountTypeDAOImpl();
	
	
	
	public List<AccountType> getAccountTypes(){
		
		return accountTypeDAO.getAllAccountTypes();
		
	}
	public AccountType getAccountById(byte accountTypeId) {
		return accountTypeDAO.getAccountTypeById(accountTypeId);
	}
	
}
