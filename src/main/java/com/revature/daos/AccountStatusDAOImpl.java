package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.AccountStatus;
import com.revature.utils.ConnectionUtil;
import com.revature.utils.GlobalUtil;

public class AccountStatusDAOImpl implements AccountStatusDAO {

	private static Logger log = LoggerFactory.getLogger(AccountStatusDAOImpl.class);
	
	@Override
	public AccountStatus getAccountStatusById(byte accountStatusId) {
		// TODO Auto-generated method stub
		try(Connection conn= ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM account_status where account_status_id = ?;";
			
			PreparedStatement  statement = conn.prepareStatement(sql);
			
			statement.setInt(1, accountStatusId);
			
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				AccountStatus accountStatus = new AccountStatus();
				accountStatus.setAccountStatusId(result.getByte("Account_status_id"));
				accountStatus.setAccountStatusName(result.getString("account_status_name"));
				return accountStatus;
			}
		}
		catch(SQLException ex) {
			GlobalUtil.printColorMessage(ex.getMessage(), "red");
			log.error(ex.getMessage());
		}
		return null;
	}

}
