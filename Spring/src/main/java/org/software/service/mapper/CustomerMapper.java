package org.software.service.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.software.models.Customer;
import org.springframework.jdbc.core.RowMapper;

public class CustomerMapper implements RowMapper<Customer>{

	@Override
	public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
		Customer customer = new Customer();
		
		customer.setId(rs.getInt("cus_codigo"));
		customer.setFirstname(rs.getString("cus_firstname"));
		customer.setLastname(rs.getString("cus_lastname")); 
		customer.setAge(rs.getInt("cus_age"));
		
		return customer;
	}

}
