package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
	Customer createCustomer(Customer customer);

	Customer updateCustomer(Long id, Customer customer);

	List<Customer> getAllCustomers(int page, int size, String sortBy, String search);

	Customer getCustomerById(Long id);

	void deleteCustomer(Long id);

	List<Customer> getAllCustomers();

	void addCustomer(Customer customer);

	Page<Customer> getAllCustomers(Pageable pageable);
}
