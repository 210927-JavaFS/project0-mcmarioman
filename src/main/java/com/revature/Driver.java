package com.revature;
import com.revature.controllers.MenuController;
import com.revature.utils.GlobalUtil;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		GlobalUtil.printColorMessage("Welcome to Revature Bank!", "cyan");
		
		MenuController menu = new MenuController();
		menu.loadUI();
		
	}

}

