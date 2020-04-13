package com.sachin.springdemo.service;

import java.util.List;

import com.sachin.springdemo.entity.Customer;

public interface CustomerService {

	List<Customer> getCustomers();

	Customer getCustomer(int theId);

	void saveCustomer(Customer theCustomer);

	void deleteCustomer(int theId);

}
