package com.revature.daos;

import com.revature.models.AccountStatus;

public interface AccountStatusDAO {
	AccountStatus getAccountStatusById(byte accountStatusId);
}
