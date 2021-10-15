package com.revature.util;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;



import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.Account;
import com.revature.models.AccountType;
import com.revature.models.Customer;
import com.revature.models.User;
import com.revature.services.AccountService;
import com.revature.services.AccountTypeService;
import com.revature.services.CustomerService;
import com.revature.services.UserService;

public class ProjectZeroUtilTest {

	/*
	 * Testing 12 out of 15 methods of the Service Layer
	 * 80% coverage
	 * 
	 * */
	
	
	public static AccountService accountService = new AccountService();
	/*7 of 9 methods tested*/
	public static UserService userService = new UserService();
	/*2 of 3 methods tested*/
	public static CustomerService customerService = new CustomerService();
	/*1 of 1 methods tested*/
	public static AccountTypeService accountTypeService = new AccountTypeService();
	/*2 of 2 methods tested*/
	public static Logger log = LoggerFactory.getLogger(ProjectZeroUtilTest.class);
	public static Account testAccount;
	public static Account testAccount2;
	public static double depositAmount;
	public static double withdrawAmount;
	public static boolean result;
	public static User testUser;
	
	@BeforeAll
	public static void setInitialValues() {
		testAccount = accountService.getAccountByNumber(1000);
		testAccount2 = accountService.getAccountByNumber(1001);
		testUser = userService.Login("bank.admin", "admin");
		depositAmount = 10;
		withdrawAmount = 10000000;
	}
	
	
	@Test
	public void testDepositFunds() {
		log.info("In testDepositFunds");
		result = accountService.depositFunds(testAccount, depositAmount);
		assertTrue(result);
	}
	@Test
	public void testValidateAccount() {
		log.info("In testValidateAccount");
		Account resultAccount = accountService.validateAccount(testAccount.getAccountNumber(), testUser);
		assertEquals(resultAccount.getAccountNumber(),  testAccount.getAccountNumber());
	}
	@Test
	public void testwithdrawFunds() {
		log.info("In testwithdrawFunds");
		result = accountService.withdrawFunds(testAccount, withdrawAmount);
		/*Withdrawal exceeds funds so it must return false*/
		assertFalse(result);
	}
	@Test
	public void testGetAccountsByUserId() {
		log.info("In testGetAccountsByUserId");
			
		List<Account> resultList = accountService.getAccountsByUserId(testAccount.getUser().getUserId());
		//The same user name is expected because the accounts belongs to the same user. 
		assertEquals(testAccount.getUser().getUserName(), resultList.get(0).getUser().getUserName());
	}
	
	@Test
	public void testGetAccountsByUserStatusId() {
		log.info("In testGetAccountsByUserStatusId");
		
		List<Account> resultList = accountService.getAccountsByUserStatusId(testAccount.getAccountStatus().getAccountStatusId());
		//The same account status is expected
		assertEquals(testAccount.getAccountStatus().getAccountStatusName(), resultList.get(0).getAccountStatus().getAccountStatusName());
	}
	
	@Test
	public void testGetAccountByNumber() {
		log.info("In testGetAccountByNumber");
		
		Account result = accountService.getAccountByNumber(testAccount.getAccountNumber());
		//Returning the same account
		assertEquals(testAccount.getAccountNumber(), result.getAccountNumber());
	}
	@Test
	public void testTransferFunds() {
		log.info("In testTransferFunds");
		
		double balance1 = testAccount.getBalance();	
		
		double balance2 = testAccount2.getBalance();
		
		
		result = accountService.transferFunds(testAccount , testAccount2, 5);
		
		Account updatedAccount1 = accountService.getAccountByNumber(testAccount.getAccountNumber());
		Account updatedAccount2 = accountService.getAccountByNumber(testAccount2.getAccountNumber());
		
		assertTrue((balance1 == updatedAccount1.getBalance() + 5) && (balance2 == updatedAccount2.getBalance() - 5)  );
	}
	
	@Test
	public void testLogin() {
		log.info("In testLogin");
		
		User resultUser = userService.Login(testAccount.getUser().getUserName(), "password");
		
		assertEquals(resultUser.getUserName(), testAccount.getUser().getUserName());
	}
	
	@Test
	public void testIsUserNameAvailable() {
		log.info("In testIsUserNameAvailable");
		
		result = userService.isUserNameAvailable(testAccount.getUser().getUserName());
		
		assertFalse(result);
	}
	
	@Test
	public void testFindCustomerByName() {
		log.info("In testFindCustomerByName");
		
		List<Customer> list = customerService.findCustomerByName(testAccount.getUser().getFirst_name());
		
		assertEquals(list.get(0).getFirst_name(), testAccount.getUser().getFirst_name());
	}
	
	@Test
	public void testGetAccountTypes() {
		log.info("In testGetAccountTypes");
		List<AccountType> accountTypes = accountTypeService.getAccountTypes();
		assertTrue(accountTypes.get(0).getClass().getName().equals("com.revature.models.AccountType"));
	}
	
	@Test
	public void testGetAccountById() {
		log.info("In testGetAccountById");
		AccountType resultAccountType = accountTypeService.getAccountById(testAccount.getAccountType().getAccountTypeId());
		assertEquals(resultAccountType.getAccountTypeName(), testAccount.getAccountType().getAccountTypeName());
	}
	
	@AfterEach
	public void clearResult() {
		result = false;
		log.info("In clearResult");
	}
	@AfterAll
	public static void clearProjectZeroUtil() {
		testAccount = null;
		testUser = null;
		log.info("in clearProjectZeroUtil");
	}
}
