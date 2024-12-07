package com.hackathon.MortgageDemo.repository;

import com.hackathon.MortgageDemo.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByAccountNumberAndAvailableBalanceGreaterThan(@NonNull Integer accountNumber, @NonNull Double availableBalance);
}