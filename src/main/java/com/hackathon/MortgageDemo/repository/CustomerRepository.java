package com.hackathon.MortgageDemo.repository;

import com.hackathon.MortgageDemo.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findById(Long id);
    Optional<Customer> findByCustomerName(String name);
}