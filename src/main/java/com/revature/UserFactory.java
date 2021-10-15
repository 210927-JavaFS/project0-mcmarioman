package com.revature;

import com.revature.models.Admin;
import com.revature.models.Customer;
import com.revature.models.Employee;
import com.revature.models.User;

public class UserFactory {
	public static User getUser(int userId, String userName, byte userRoleId) {
		
		switch(userRoleId) {
		case 1:
			return new Customer(userId, userName, userRoleId);
		case 2:
			return new Employee(userId, userName, userRoleId);
		case 3:
			return new Admin(userId, userName, userRoleId);
		default:
			throw new RuntimeException("User Role id invalid");
		}
		
	}
}
