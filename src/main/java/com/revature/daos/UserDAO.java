package com.revature.daos;

import com.revature.models.Customer;
import com.revature.models.User;

public interface UserDAO {
	User getUserById(int userId);
	User getUserByUserNameAndPassword(String userName, String password);
	boolean registerNewCustomer(Customer customer);
	boolean isUserNameAvailable(String userName);
}
