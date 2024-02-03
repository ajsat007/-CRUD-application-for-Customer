package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        if (customerRepository.existsById(id)) {
            customer.setId(id);
            return customerRepository.save(customer);
        } else {
            throw new RuntimeException("Customer not found with id: " + id);
        }
    }

    @Override
    public List<Customer> getAllCustomers(int page, int size, String sortBy, String search) {
        try {
            // Check if sortBy is a valid field in Customer entity
            Customer.class.getDeclaredField(sortBy);
        } catch (NoSuchFieldException e) {
            // If the field doesn't exist, set a default sorting field or handle the error as needed
            sortBy = "id"; // Default to sorting by customer ID if sortBy is invalid
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        if (search != null && !search.isEmpty()) {
            Page<Customer> customerPage = customerRepository.searchCustomers(search, pageable);
            return customerPage.getContent();
        } else {
            Page<Customer> customerPage = customerRepository.findAll(pageable);
            return customerPage.getContent();
        }
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @Override
    public void deleteCustomer(Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
        } else {
            throw new CustomerNotFoundException(id);
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        // Fetch the list of customers from the repository
        return customerRepository.findAll();
    }

    @Override
    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public Page<Customer> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }
}
