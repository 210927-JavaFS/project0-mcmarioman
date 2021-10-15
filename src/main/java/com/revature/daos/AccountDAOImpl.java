package com.revature.daos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.controllers.BankController;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.utils.ConnectionUtil;
import com.revature.utils.GlobalUtil;

public class AccountDAOImpl implements AccountDAO {
	
	private AccountTypeDAO accountTypeDAO = new AccountTypeDAOImpl();
	private AccountStatusDAO accountStatusDAO = new AccountStatusDAOImpl();
	private UserDAO userDAO = new UserDAOImpl();
	
	private static Logger log = LoggerFactory.getLogger(AccountDAOImpl.class);
	
	@Override
	public List<Account> getAccountsByUserId(int UserId) {
		try(Connection conn= ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM account a WHERE user_id = ? order by account_number;";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setInt(1, UserId);
			
			ResultSet result = statement.executeQuery();
			
			List<Account> list = new ArrayList<>();
			
			while(result.next()) {
				Account c = new Account();
				c.setAccountNumber(result.getInt("account_number"));				
				
				c.setAccountType(accountTypeDAO.getAccountTypeById(result.getByte("account_type_id")));
				c.setAccountStatus(accountStatusDAO.getAccountStatusById(result.getByte("account_status_id")));
		
				c.setBalance(result.getDouble("balance"));
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");				
				c.setDateOpened(dateFormat.format(result.getDate("date_opened")));
				
				c.setUser(userDAO.getUserById(result.getInt("user_id")));
				
				list.add(c);
			}
			
			return list;
			
		}catch(SQLException ex) {
			GlobalUtil.printColorMessage(ex.getMessage(), "red");
			log.error(ex.getMessage());
		}
		return null;
	}


	@Override
	public boolean createNewAccount(Account account) {
		// TODO Auto-generated method stub
		
		try(Connection conn= ConnectionUtil.getConnection()){
			
			
			String sql = "INSERT INTO account (account_type_id, user_id, balance, account_status_id, date_opened) VALUES (?,?,0,1,now());";
			PreparedStatement statement = conn.prepareStatement(sql);
			
			int cont = 0;
			
			statement.setInt(++cont, account.getAccountType().getAccountTypeId());
			statement.setInt(++cont, account.getUser().getUserId());

			
			statement.execute();
			
			return true;
			
		}catch(SQLException ex) {
			GlobalUtil.printColorMessage(ex.getMessage(), "red");
			log.error(ex.getMessage());
		}
		
		
		return false;
	}

	@Override
	public boolean updateAccountBalance(Account account) {
		// TODO Auto-generated method stub
		try(Connection conn= ConnectionUtil.getConnection()){
			String sql = "UPDATE account SET balance = ? WHERE account_number = ?;";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			int cont = 0;
			
			statement.setDouble(++cont, account.getBalance());
			statement.setInt(++cont, account.getAccountNumber());
			
			statement.execute();
			
			return true;
			
		}catch(SQLException ex) {
			GlobalUtil.printColorMessage(ex.getMessage(), "red");
			log.error(ex.getMessage());
		}
		return false;
	}

	@Override
	public Account getAccountByNumber(int account_number) {
		// TODO Auto-generated method stub
		try(Connection conn= ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM account a WHERE account_number = ?;";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setInt(1, account_number);
			
			ResultSet result = statement.executeQuery();
			

			
			if(result.next()) {
				Account c = new Account();
				c.setAccountNumber(result.getInt("account_number"));				
				
				c.setAccountType(accountTypeDAO.getAccountTypeById(result.getByte("account_type_id")));
				c.setAccountStatus(accountStatusDAO.getAccountStatusById(result.getByte("account_status_id")));
		
				c.setBalance(result.getDouble("balance"));
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");				
				c.setDateOpened(dateFormat.format(result.getDate("date_opened")));
				
				c.setUser(userDAO.getUserById(result.getInt("user_id")));
				
				return c;
			}
	
			
		}catch(SQLException ex) {
			GlobalUtil.printColorMessage(ex.getMessage(), "red");
			log.error(ex.getMessage());
		}
		return null;
	}

	@Override
	public Account validateAccount(int accountNumber, User user) {
		// TODO Auto-generated method stub
		
		Account account = getAccountByNumber(accountNumber);
		
		if(account == null) {
			return null;
		}
		
		/*If account belongs to User or if User is an Administrator, also the account must be active*/
		if(( (account.getUser().getUserId() == user.getUserId()) || (user.getUserRoleId() == 3) ) && account.getAccountStatus().getAccountStatusName().toLowerCase().equals("active")) {
			return account;
		}
		
		return null;
	}

	@Override
	public List<Account> getAccountsByUserStatusId(int AccountStatusId) {
		// TODO Auto-generated method stub
		
		try(Connection conn= ConnectionUtil.getConnection()){
			
			String sql = "Select * from account where account_status_id = ?;";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setInt(1, AccountStatusId);
			
			ResultSet result = statement.executeQuery();
			
			List<Account> list = new ArrayList<>();
			
			while(result.next()) {
				Account c = new Account();
				c.setAccountNumber(result.getInt("account_number"));				
				
				c.setAccountType(accountTypeDAO.getAccountTypeById(result.getByte("account_type_id")));
				c.setAccountStatus(accountStatusDAO.getAccountStatusById(result.getByte("account_status_id")));
		
				c.setBalance(result.getDouble("balance"));
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");				
				c.setDateOpened(dateFormat.format(result.getDate("date_opened")));
				
				c.setUser(userDAO.getUserById(result.getInt("user_id")));
				
				list.add(c);
			}
			
			return list;
			
		}catch(SQLException ex) {
			GlobalUtil.printColorMessage(ex.getMessage(), "red");
			log.error(ex.getMessage());
		}
		
		return null;
	}
	
	@Override
	public boolean changeAccountStatus(int accountNumber, int accountStatusId) {
		
		try(Connection conn= ConnectionUtil.getConnection()){
			
			String sql = "UPDATE account set account_status_id = ?, date_approved = now() where account_number = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			
			int cont = 0;
			
			statement.setInt(++cont, accountStatusId);
			statement.setInt(++cont, accountNumber);
			
			statement.execute();
			
			return true;
			
		}catch(SQLException ex) {
			GlobalUtil.printColorMessage(ex.getMessage(), "red");
			log.error(ex.getMessage());
		}
		return false;
	}

	@Override
	public boolean transferFunds(Account accountFrom, Account accountTo, double amount) {
		// TODO Auto-generated method stub
		try(Connection conn= ConnectionUtil.getConnection()){
			//Calling stored procedure
			String sql = "CALL transfer_funds (?, ?, ?::numeric(11,2));";
			
			CallableStatement statement = conn.prepareCall(sql);
			
			int cont = 0;
			
			statement.setInt(++cont, accountFrom.getAccountNumber());
			statement.setInt(++cont, accountTo.getAccountNumber());
			statement.setDouble(++cont, amount);

			statement.execute();
			
			return true;
			
		}catch(SQLException ex) {
			System.out.println(ex.getMessage()); 
			log.error(ex.getMessage());
		}
		return false;
	}

}
