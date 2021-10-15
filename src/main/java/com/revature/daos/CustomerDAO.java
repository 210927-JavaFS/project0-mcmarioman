package com.revature.daos;

import java.util.List;

import com.revature.models.Customer;

public interface CustomerDAO {
	List<Customer> findCustomerByName(String name);
}
