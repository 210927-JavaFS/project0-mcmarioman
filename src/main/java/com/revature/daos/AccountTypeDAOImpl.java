package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.AccountType;
import com.revature.utils.ConnectionUtil;
import com.revature.utils.GlobalUtil;


public class AccountTypeDAOImpl implements AccountTypeDAO{

	private static Logger log = LoggerFactory.getLogger(AccountTypeDAOImpl.class);
	
	@Override
	public List<AccountType> getAllAccountTypes() {
		// TODO Auto-generated method stub
		try(Connection conn= ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM account_type;";
			
			Statement statement = conn.createStatement();
			
			ResultSet result = statement.executeQuery(sql);
			
			List<AccountType> list = new ArrayList<>();
			
			//ResultSets have a cursor similar to scanner or other io classes
			//it can be used with a while loop to iterate all the data
			
			while(result.next()) {
				AccountType accountType = new AccountType();
				accountType.setAccountTypeId(result.getByte("Account_Type_Id"));
				accountType.setAccountTypeName(result.getString("account_type_name"));
				list.add(accountType);
			}
			
			return list;
			
		}catch(SQLException ex) {
			GlobalUtil.printColorMessage(ex.getMessage(), "red");
			log.error(ex.getMessage());
		}
		
		return null;
	}

	@Override
	public AccountType getAccountTypeById(byte AccountTypeId) {
		// TODO Auto-generated method stub
		try(Connection conn= ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM account_type where Account_Type_Id = ?;";
			
			PreparedStatement  statement = conn.prepareStatement(sql);
			
			statement.setInt(1, AccountTypeId);
			
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				AccountType accountType = new AccountType();
				accountType.setAccountTypeId(result.getByte("account_type_id"));
				accountType.setAccountTypeName(result.getString("account_type_name"));
				return accountType;
			}
		}
		catch(SQLException ex) {
			GlobalUtil.printColorMessage(ex.getMessage(), "red");
			log.error(ex.getMessage());
		}
		return null;
	}

}
