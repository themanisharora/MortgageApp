package com.hackathon.MortgageDemo.service;

import com.hackathon.MortgageDemo.model.TransactionRequest;

import java.math.BigDecimal;

public interface MortgageService {
    Boolean postTransaction(TransactionRequest transactionRequest);
}
