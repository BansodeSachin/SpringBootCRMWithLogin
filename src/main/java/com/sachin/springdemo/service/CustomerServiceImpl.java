package com.sachin.springdemo.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sachin.springdemo.dao.CustomerRepository;
import com.sachin.springdemo.entity.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	@Transactional
	public List<Customer> getCustomers() {
		
		List<Customer> theCustomers = new ArrayList<>();
		
		customerRepository.findAll().forEach(theCustomers :: add);
		
		return theCustomers;
	}

	@Override
	@Transactional
	public Customer getCustomer(int theId) {
		return customerRepository.findById(theId).orElse(null);
	}

	@Override
	@Transactional
	public void saveCustomer(Customer theCustomer) {
		customerRepository.save(theCustomer);
	}

	@Override
	@Transactional
	public void deleteCustomer(int theId) {
		customerRepository.deleteById(theId);
	}

}
