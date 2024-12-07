package com.hackathon.MortgageDemo.service.Impl;

import com.hackathon.MortgageDemo.entity.Account;
import com.hackathon.MortgageDemo.entity.Transaction;
import com.hackathon.MortgageDemo.model.TransactionRequest;
import com.hackathon.MortgageDemo.repository.AccountRepository;
import com.hackathon.MortgageDemo.repository.TransactionRepository;
import com.hackathon.MortgageDemo.service.MortgageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@Slf4j
@RequiredArgsConstructor
public class MortgageServiceImpl implements MortgageService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    @Override
    @Transactional
    public Boolean postTransaction(TransactionRequest transactionRequest) {
        try{
            Account fromAccountDetails = accountRepository.findByAccountNumberAndAvailableBalanceGreaterThan(transactionRequest.getFromAccount(),transactionRequest.getAmount());
            Account toAccountDetails = accountRepository.findByAccountNumberAndAvailableBalanceGreaterThan(transactionRequest.getToAccount(),transactionRequest.getAmount());
            if(!ObjectUtils.isEmpty(fromAccountDetails)
                    && fromAccountDetails.getAvailableBalance()
                    > transactionRequest.getAmount()){

                    directionCall(fromAccountDetails,toAccountDetails,transactionRequest.getAmount());
                    Transaction transaction = Transaction.builder().transactionAmount(transactionRequest.getAmount()).transactionRemarks(
                                    transactionRequest.getRemarks()
                            ).sourceAccount(fromAccountDetails)
                            .targetAccount(transactionRequest.getToAccount()).build();
                   return performTransaction(transaction);
            }
        } catch (Exception e){
            log.error(e.getMessage());
        }
        return false;
    }
    private void directionCall(Account fromAccount,Account toAccount, Double requestAmount) throws Exception{
        if(!ObjectUtils.isEmpty(fromAccount)) {
            accountRepository.updateAvailableBalanceByAccountNumberAllIgnoreCase(fromAccount.getAvailableBalance()-requestAmount
                    ,fromAccount.getAccountNumber());
        }
        if(!ObjectUtils.isEmpty(toAccount)) {
            accountRepository.updateAvailableBalanceByAccountNumberAllIgnoreCase(toAccount.getAvailableBalance()+requestAmount
                    ,toAccount.getAccountNumber());
        }
    }

    protected Boolean performTransaction(Transaction transaction) {

        transactionRepository.save(transaction);
        return true;
    }
}
