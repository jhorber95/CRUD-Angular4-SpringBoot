package org.software.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.software.models.Customer;
import org.software.service.CustomerService;
import org.software.service.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
 

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	
	
	
	private final String INSERT_SQL = "INSERT INTO customer"
										+ "	(cus_firstname, cus_lastname, cus_age)"
										+ "VALUES (?, ?, ?)";
	
	private final String UPDATE_SQL = "UPDATE customer SET "
										+ "cus_firstname = ?,"
										+ "cus_lastname = ?, "
										+ "cus_age = ? "
										+ " WHERE cus_codigo = ?";
	
	private final String DELETE_SQL = "DELETE FROM customer WHERE cus_codigo = ?";
	
	private final String FETCH_SQL = "SELECT * FROM customer";
	
	private final String FETCH_SQL_BY_ID = "SELECT * FROM customer WHERE cus_codigo =?";
	
	private final String FETCH_SQL_BY_NAME = "SELECT * FROM customer WHERE cus_firstname like '%?%'";
	
	
	
	@Override
	public Customer finById(int id) {
		
		return (Customer) jdbcTemplate.queryForObject(FETCH_SQL_BY_ID, new Object [] {id}, new CustomerMapper());
	}

	@Override
	public Customer fingByName(String firstName) {
		
		return (Customer) jdbcTemplate.queryForObject(FETCH_SQL_BY_NAME, new Object [] {firstName}, new CustomerMapper());
	}

	@Override
	public Customer createCustomer(Customer customer) {
		KeyHolder holder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, customer.getFirstname());
				ps.setString(2, customer.getLastname());
				ps.setInt(3, customer.getAge());
				return ps;
			}
		}, holder);
		
		
		int newCustomer = holder.getKey().intValue();
		System.out.println("newCustomerId: " + newCustomer);
		customer.setId(newCustomer);
		
		return customer;
	}

	@Override
	public Customer updateCustomer(Customer customer) {
		
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(UPDATE_SQL);
				
				ps.setString(1, customer.getFirstname());
				ps.setString(2, customer.getLastname());
				ps.setInt(3, customer.getAge());
				ps.setInt(4, customer.getId());
				return ps;
			}
		});
		return customer;
	}

	@Override
	public void deleteCustomer(int id) {
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(DELETE_SQL);
				ps.setInt(1, id);
				return ps;
			}
		});
		
	}

	@Override
	public List<Customer> findAllCustomer() {
		
		return jdbcTemplate.query(FETCH_SQL,new CustomerMapper());
	}

	@Override
	public boolean customerExist(Customer customer) {
		// TODO Auto-generated method stub
		return false;
	}

}
