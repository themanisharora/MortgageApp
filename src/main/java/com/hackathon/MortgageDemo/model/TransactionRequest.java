package com.hackathon.MortgageDemo.model;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequest {
    @NonNull
    private Integer fromAccount;
    @NonNull
    private Integer toAccount;
    @NonNull
    private Double amount;
    private String remarks;
}
