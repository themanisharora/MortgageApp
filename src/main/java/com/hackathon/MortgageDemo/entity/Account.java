package com.hackathon.MortgageDemo.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackathon.MortgageDemo.entity.enumEntity.AccountType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "Account")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Account {

    @Id
    @Column(name = "Account_Number", columnDefinition = "VARCHAR(30)")
    private Integer accountNumber; // Primary Key

    @Column(name = "Account_Type", columnDefinition = "VARCHAR(20)")
    @Enumerated(EnumType.STRING)
    private AccountType type;

    @Column(name = "Available_Balance", columnDefinition = "DECIMAL(7,2)",nullable = false)
    private Double availableBalance;

    @ManyToOne
    @JoinColumn(name = "Customer_Id", nullable = false)
    private Customer customer;

    @Column(name = "Create_Timestamp", columnDefinition = "DATETIME")
    private LocalDateTime createTimestamp;

    @Column(name = "Update_Timestamp", columnDefinition = "DATETIME")
    private LocalDateTime updateTimestamp;

    @Column(name = "Updated_By", columnDefinition = "VARCHAR(40)",nullable = false)
    private String updatedBy;

}
