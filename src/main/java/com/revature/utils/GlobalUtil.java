package com.revature.utils;

import java.util.Scanner;

public class GlobalUtil {
	public static final String ANSI_YELLOW = "\u001B[33m";	
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_GREEN = "\u001B[32m";
	
	public static void printColorMessage(String msg, String color) {
		System.out.println("\n" + addColorToString(msg, color) + "\n");
	}
	
	public static String addColorToString(String text, String color) {
		switch(color) {
		case "yellow":
			color = ANSI_YELLOW;
			break;
		case "red":
			color = ANSI_RED;
			break;
		case "cyan":
			color = ANSI_CYAN;
			break;
		case "green":
			color = ANSI_GREEN;
			break;
		default:
			color = ANSI_RESET;
			break;
		
		}
		
		return  color + text  + ANSI_RESET;
	}
	
	
	public static void waitForEnterKey(Scanner scan) {
		System.out.println("\nPress ENTER key to return to menu...");
		
		scan.nextLine();

		printColorMessage("What do you want to do next?", "cyan");
	}
	
	public static double validateUserAmountInput(Scanner scan) {
		
		double amount = 0;
		
		try {
			amount = Double.parseDouble(scan.nextLine());
		} catch (Exception e) {
			GlobalUtil.printColorMessage("Invalid amount!", "red");
			return 0;
		}
		
		if(amount <= 0) 
		{
			GlobalUtil.printColorMessage("Amount must be greater than zero!", "yellow");
			return 0;
		}
		
		return amount;
	}
}
