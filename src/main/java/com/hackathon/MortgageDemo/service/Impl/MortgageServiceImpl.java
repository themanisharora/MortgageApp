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

    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;
    @Override
    public Boolean postTransaction(TransactionRequest transactionRequest) {
        try{
            Account account = accountRepository.findByAccountNumberAndAvailableBalanceGreaterThan(transactionRequest.getFromAccount(),transactionRequest.getAmount());
            Transaction transaction = Transaction.builder().transactionAmount(transactionRequest.getAmount()).transactionRemarks(
                    transactionRequest.getRemarks()
            ).sourceAccount(account)
                    .targetAccount(transactionRequest.getToAccount()).build();
            if(!ObjectUtils.isEmpty(account)){
                try{
                    performTransaction(transaction);
                }catch(Exception e){
                    log.error(e.getMessage());
                }
            }
        } catch (Exception e){
            log.error(e.getMessage());
        }
        return true;
    }
    @Transactional
    protected Boolean performTransaction(Transaction transaction) {

        transactionRepository.save(transaction);
        return true;
    }
}
