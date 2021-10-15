package com.revature.services;

import com.revature.daos.UserDAO;
import com.revature.daos.UserDAOImpl;
import com.revature.models.Customer;
import com.revature.models.User;

public class UserService {
	
	
	private UserDAO userDAO = new UserDAOImpl();
	
	
	public User Login(String userName, String password) {
		
		return userDAO.getUserByUserNameAndPassword(userName, password);
		
	}
	
	public boolean registerNewCustomer(Customer customer) {
		return userDAO.registerNewCustomer(customer);
	}
	
	public boolean isUserNameAvailable(String userName) {
		return userDAO.isUserNameAvailable(userName);
	}
	
}
