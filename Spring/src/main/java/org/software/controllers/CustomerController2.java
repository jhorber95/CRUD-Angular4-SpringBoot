package org.software.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.software.models.Customer;
import org.software.service.CustomerService;
import org.software.util.CustomErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("api2")
public class CustomerController2 {
	
	public static final Logger logger = LoggerFactory.getLogger(CustomerController2.class);
	
	@Autowired
	CustomerService customerService;
	
	
	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "/customer", method = RequestMethod.GET)
	public List<Customer> findAll(){
		return customerService.findAllCustomer();
	}
	
	
	/*
	 * TODO fetch a single customer
	 * 
	 * */
	
	@RequestMapping(value = "/customer/{codigo}", method = RequestMethod.GET)
	public ResponseEntity<?> getCustomer(@PathVariable("codigo") int codigo){
		
		logger.info("Fetching Customer with code {} ",codigo);
		
		Customer customer = customerService.finById(codigo);
		
		if(customer == null){
			logger.error("Customer with code {} not found", codigo);
			
			return new ResponseEntity(new CustomErrorType("Customer with code "
					+ codigo + " not found"), HttpStatus.NOT_FOUND);
		}
		
		
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}
	
	/*
	 * 		TODO  Create a  customer 
	 * 
	 * 
	 * */
	
	@RequestMapping(value = "/customer", method = RequestMethod.POST)
	public ResponseEntity<?>  createCustomer(@RequestBody Customer customer, UriComponentsBuilder ucBuilder ){
		
		logger.info("Creating Customer: {}", customer.getId() );
		
		if(customerService.customerExist(customer)){
			logger.error("Unable to create. A Customer with name {} already exist.", customer.getFirstname());
			
			return new ResponseEntity(new CustomErrorType("Unable to create. A Customer with name"
					+ customer.getFirstname() + " already exist"), HttpStatus.CONFLICT);
		}
		
		customerService.createCustomer(customer);
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setLocation(ucBuilder.path("api2/customer/{codigo}").buildAndExpand(customer.getId()).toUri());

		return new ResponseEntity<String>(headers, HttpStatus.CREATED) ;
	}
	
	/*
	 * TODO Update a Customer
	 * 
	 * */
	@RequestMapping(value = "/customer/{codigo}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateCustomer(@PathVariable("codigo") int codigo, @RequestBody Customer customer){
		
		logger.info("Updating Customer: {}", codigo);
		
		Customer currentCustomer = customerService.finById(codigo);
		
		if(currentCustomer == null){
			logger.error("Unable to update. Customer with code {} no exist ", codigo);
			
			return new ResponseEntity(new CustomErrorType("Unable to upate. Customer with id " 
            		+ codigo + " not found."), HttpStatus.NOT_FOUND);
		}
		
		currentCustomer.setFirstname(customer.getFirstname());
		currentCustomer.setLastname(customer.getLastname());
		currentCustomer.setAge(customer.getAge());
		
		customerService.updateCustomer(currentCustomer);
		
		
		return new ResponseEntity<Customer> (currentCustomer, HttpStatus.OK);
	}
	
	/*
	 * 
	 * TODO  Deleting a Customer
	 * 
	 * */
	@RequestMapping(value = "/customer/{codigo}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteCustomer(@PathVariable("codigo") int codigo){
		
		logger.info("Fetching and deleting Customer with code {}", codigo);
		
		Customer currentCustomer = customerService.finById(codigo);
		
		if(currentCustomer == null){
			logger.error("Unable to delete. Customer with code {} no found", codigo);
			
			return new ResponseEntity(new CustomErrorType("Unable to delete. Customer with id " 
            		+ codigo + " not found."), HttpStatus.NOT_FOUND);
			
		}
		
		customerService.deleteCustomer(codigo);
					
		return new ResponseEntity<Customer> (currentCustomer, HttpStatus.NO_CONTENT);
	}

}
