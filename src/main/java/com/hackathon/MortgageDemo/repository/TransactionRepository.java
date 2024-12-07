package com.hackathon.MortgageDemo.repository;

import com.hackathon.MortgageDemo.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    void save();
}