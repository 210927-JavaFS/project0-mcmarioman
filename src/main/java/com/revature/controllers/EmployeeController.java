package com.revature.controllers;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.revature.models.User;

import com.revature.utils.GlobalUtil;

public class EmployeeController extends BankController {
	
	private static Logger log = LoggerFactory.getLogger(EmployeeController.class);
	
	public  void showEmployeeMenu(User employee) {
		GlobalUtil.printColorMessage("You are logged in as " + employee.getUserName() + "\nWhat do you want to do today?", "cyan");
		boolean done = false;
		
		String option = "";
		
		
		/*Validate user input*/
		while(!done) {
			
			boolean isValid = true;
			
			System.out.println("1 - Customer search.");
			System.out.println("2 - View account information.");
			System.out.println("3 - View account requests.");
			System.out.println("4 - Log out.");
			
			option = scan.nextLine();
			
			switch (option) {
			case "1":
				log.info("In to user search.");
				loadFindCustomerUI();
				break;
			case "2":
				log.info("In to view account info.");
				loadViewAccountInfoUI();
				break;
			case "3":
				log.info("In to approve account requests.");
				loadApproveAccountUI();
				break;
			case "4":
				done =true;
				break;
			default:
				GlobalUtil.printColorMessage("Please choose a valid option.", "yellow");
					isValid = false;
					break;
			}
			if(!option.equals("4") && isValid) {
				GlobalUtil.waitForEnterKey(scan);
			}
		}
		
		System.out.println("You have been logged out. Have a nice day!");
		log.info("Session ends.");
	}
	
	


}
