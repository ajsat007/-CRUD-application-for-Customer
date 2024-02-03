package com.example.demo.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

 // Example of a custom query method for searching customers by a keyword in their properties
 @Query("SELECT c FROM Customer c WHERE LOWER(c.firstName) LIKE LOWER(CONCAT('%', ?1, '%')) OR " +
         "LOWER(c.lastName) LIKE LOWER(CONCAT('%', ?1, '%')) OR " +
         "LOWER(c.email) LIKE LOWER(CONCAT('%', ?1, '%'))")
 Page<Customer> searchCustomers(String keyword, Pageable pageable);







}
