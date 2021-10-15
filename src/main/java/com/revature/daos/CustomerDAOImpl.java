package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.Customer;
import com.revature.utils.ConnectionUtil;

public class CustomerDAOImpl implements CustomerDAO {
	
	private static Logger log = LoggerFactory.getLogger(CustomerDAOImpl.class);

	@Override
	public List<Customer> findCustomerByName(String name) {
		// TODO Auto-generated method stub
		
		try(Connection conn= ConnectionUtil.getConnection()){
			
			String sql = "SELECT u.user_id, u.user_name, u.user_role_id, u.first_name, u.last_name, c.email, c.phone_number FROM users u INNER JOIN customer c ON u.user_id = c.user_id WHERE upper(u.first_name)  LIKE ? OR upper(u.last_name) LIKE ?;";
			
			int cont = 0;
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			String filter = "%" + name.toUpperCase() + "%";
			
			statement.setString(++cont, filter);
			statement.setString(++cont, filter);
			
			ResultSet result = statement.executeQuery();
			
			
			List<Customer> list = new ArrayList<>();
			
			while(result.next()) {
				
				Customer c = new Customer(result.getInt("user_id"), result.getString("user_name"), result.getByte("User_Role_Id"));
				c.setFirst_name(result.getString("first_name"));
				c.setLast_name(result.getString("last_name"));
				c.setEmail(result.getString("email"));
				c.setPhoneNumber(result.getString("phone_number"));
				
				list.add(c);	
			}			
			return list;
		}catch(SQLException ex) {
			System.out.println(ex.getMessage());
			log.error(ex.getMessage());
		}
		return null;
	}

}
