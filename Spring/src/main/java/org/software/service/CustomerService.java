package org.software.service;

import java.util.List;

import org.software.models.Customer;

public interface CustomerService {
	
	Customer finById(int id);
	
	Customer fingByName (String firstName);
	
	Customer createCustomer(Customer customer);
	
	Customer updateCustomer(Customer customer);
	
	void deleteCustomer (int id);
	
	List<Customer> findAllCustomer();
	
	boolean customerExist (Customer customer);
	
 
}
