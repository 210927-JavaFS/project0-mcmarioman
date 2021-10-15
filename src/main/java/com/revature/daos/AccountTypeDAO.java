package com.revature.daos;

import java.util.List;

import com.revature.models.AccountType;

public interface AccountTypeDAO {
	List<AccountType> getAllAccountTypes();
	AccountType getAccountTypeById(byte AccountTypeId);
}
