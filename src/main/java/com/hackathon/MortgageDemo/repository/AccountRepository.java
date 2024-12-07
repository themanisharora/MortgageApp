package com.hackathon.MortgageDemo.repository;

import com.hackathon.MortgageDemo.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByAccountNumberAndAvailableBalanceGreaterThan(@NonNull Integer accountNumber, @NonNull Double availableBalance);

    Account findByAccountNumber(@Nullable Integer accountNumber);

    @Transactional
    @Modifying
    @Query("update Account a set a.availableBalance = ?1 where a.accountNumber = ?2")
    int updateAvailableBalanceByAccountNumberAllIgnoreCase(Double availableBalance, Integer accountNumber);
}