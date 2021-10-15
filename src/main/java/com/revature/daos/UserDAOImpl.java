package com.revature.daos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.UserFactory;
import com.revature.models.Customer;
import com.revature.models.User;
import com.revature.utils.ConnectionUtil;

public class UserDAOImpl implements UserDAO {

	private static Logger log = LoggerFactory.getLogger(UserDAOImpl.class);
	
	@Override
	public User getUserById(int userId) {
		// TODO Auto-generated method stub
		try(Connection conn= ConnectionUtil.getConnection()){
			
			String sql = "select * from users where user_id = ?";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setInt(1, userId);
			
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				User user = new User(result.getInt("user_id"), result.getString("user_name"), result.getByte("User_Role_Id"));
				user.setFirst_name(result.getString("first_name"));
				user.setLast_name(result.getString("last_name"));
				return user;
			}
			
		}
		catch(SQLException ex) {
			System.out.println(ex.getMessage());
			log.error(ex.getMessage());
		}
		return null;
	}

	@Override
	public User getUserByUserNameAndPassword(String userName, String password) {
		// TODO Auto-generated method stub
		try(Connection conn= ConnectionUtil.getConnection()){
			String sql = "select * from users where user_name = ?;";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(1, userName);
			
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				
				
				
				String encryptedPassword =  result.getString("user_password");
				StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
			
				if(passwordEncryptor.checkPassword(password, encryptedPassword)) {
					User user = UserFactory.getUser(result.getInt("user_id"), result.getString("user_name"), result.getByte("User_Role_Id"));
					user.setFirst_name(result.getString("first_name"));
					user.setLast_name(result.getString("last_name"));
					return user;
				}
				
				return null;
				
			}
		}
		catch(SQLException ex) {
			System.out.println(ex.getMessage());
			log.error(ex.getMessage());
		}
		return null;
	}

	@Override
	public boolean registerNewCustomer(Customer customer) {
		// TODO Auto-generated method stub
		try(Connection conn= ConnectionUtil.getConnection()){
			String sql = "CALL insert_customer (?, ?, ?, ?, ?, ?);";
			
			CallableStatement statement = conn.prepareCall(sql);
			
			
			StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
			String encryptedPassword = passwordEncryptor.encryptPassword(customer.getPassword());
			
			int cont = 0;
			
			statement.setString(++cont, customer.getUserName());
			statement.setString(++cont, encryptedPassword);
			statement.setString(++cont, customer.getFirst_name());
			statement.setString(++cont, customer.getLast_name());
			statement.setString(++cont, customer.getEmail());
			statement.setString(++cont, customer.getPhoneNumber());

			statement.execute();
			
			return true;
		}
		catch(SQLException ex) {
			System.out.println(ex.getMessage());
			log.error(ex.getMessage());
		}
		return false;
	}

	@Override
	public boolean isUserNameAvailable(String userName) {
		// TODO Auto-generated method stub
		try(Connection conn= ConnectionUtil.getConnection()){
			
			String sql = "select count(*) as total from users where upper(user_name) like ?";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(1, userName.toUpperCase());
			
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				if(result.getInt("total") > 0) {
					return false;
				}
			}
			
			return true;
			
		}
		catch(SQLException ex) {
			System.out.println(ex.getMessage());
			log.error(ex.getMessage());
		}
		return false;
	}

}
