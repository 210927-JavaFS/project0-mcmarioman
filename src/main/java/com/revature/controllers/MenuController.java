package com.revature.controllers;

import java.io.Console;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.revature.models.Customer;
import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.utils.GlobalUtil;

public class MenuController {
	
	private User user;
	private Scanner input = new Scanner(System.in);
	private static Logger log = LoggerFactory.getLogger(MenuController.class);
	
	public void loadUI(){

		log.info("Application starts.");
		boolean displayMenu = true;
		
		while(displayMenu) {
			byte option = getLoginChoice();
			
			if(option == 1) {
				log.info("In to log in module");
				if(getCredentials()) {
					displayMenu = false;
				}else {
					String msg = "Invalid username or password!";
					log.warn(msg);
					System.out.println(msg);
				}				
				
			}else if(option == 2 ) {
				log.info("In to register new user module");
				loadRegisterUserUI();
			}else {
				log.info("Session ends.");
				System.out.println("Thank you for visiting Revature Bank. Have a nide day!");
				return;
			}
			
		}		
		
		MDC.put("User Name", user.getUserName());
		
		byte idUserRole = user.getUserRoleId();
		
		switch (idUserRole) {
		case 1:
			CustomerController cc = new CustomerController();
			log.info("Logged in as customer.");
			cc.showCustomerMenu(user);
			break;
		case 2:
			EmployeeController ec = new EmployeeController();
			log.info("Logged in as employee.");
			ec.showEmployeeMenu(user);
			break;
		case 3:
			AdminController ac = new AdminController();
			log.info("Logged in as admin.");
			ac.showAdminMenu(user);
		}
		
	}

	private byte getLoginChoice() {
		System.out.println("What would you like to do?");
		System.out.println("1 - Login");
		System.out.println("2 - Register");
		System.out.println("3 - Exit");

		
		String option = "";
		boolean isValid = false;
		/*Validate user input*/
		while(!isValid) {
			
			option = input.nextLine();
			
			switch (option) {
				case "1":
				case "2":
				case "3":
					isValid =true;
					break;
				default:
					GlobalUtil.printColorMessage("Please choose a valid option.", "yellow");
					break;
			}
		}
		
		return Byte.parseByte(option);
	}

	private boolean getCredentials() {
		
		UserService userService = new UserService();
		
		System.out.println("Username:");
		String userName = input.nextLine();
		System.out.println("Password:");
		String password = input.nextLine();
		
		//Console cons = System.console();
		
		/*if(cons == null) {
			System.out.println("no console");
			return false;
		}
		
		char[] password = null;
		
		 if ((cons = System.console()) != null &&
			     (password = cons.readPassword("[%s]", "Password:")) != null) {
			 System.out.println(password.toString());
		 }*/
		user = userService.Login(userName, password.toString());
		
		if(user == null) {
			return false;
		}
		
		return true;
	}
	
	private void loadRegisterUserUI() {
		System.out.println("\nNew user registration...");
		
		GlobalUtil.printColorMessage("To register please provide the information below:", "cyan");
		
		System.out.println("User Name:");
		
		String userName = input.nextLine();
		
		UserService us = new UserService();
		
		if(!us.isUserNameAvailable(userName)){
			log.warn("User name is not available.");
			GlobalUtil.printColorMessage("User name is not available. Please select a different user name.", "yellow");
			return;
		}
		System.out.println("Password:");
		
		String password = input.nextLine();
		
		System.out.println("Confirm password:");
		
		String confirmPassword = input.nextLine();
		
		if(!password.equals(confirmPassword)) {
			log.warn("Passwords do not match");
			GlobalUtil.printColorMessage("Passwords do not match. Please start again.", "yellow");
			return;
		}
		
		
		System.out.println("First Name:");
		String firstName = input.nextLine();
		System.out.println("Last Name:");
		String lastName = input.nextLine();
		System.out.println("Email:");
		String email = input.nextLine();
		System.out.println("Phone Number:");
		String phoneNumber = input.nextLine();
		
		Customer customer = new Customer(userName, password, firstName, lastName, email, phoneNumber);
		
		if(us.registerNewCustomer(customer)) {
			log.info("User registered successfully");
			GlobalUtil.printColorMessage("You have registered successfully. You can now log in to your account. ", "green");
		}else {
			log.warn("Registering failed");
			GlobalUtil.printColorMessage("We cannot complete your registration at this moment. Please try again later.", "yellow");
		}
		
		GlobalUtil.waitForEnterKey(input);
	}
	
	

}
