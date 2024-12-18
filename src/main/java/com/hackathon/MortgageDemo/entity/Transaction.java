package com.hackathon.MortgageDemo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "Transaction")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id; // Primary Key

    @ManyToOne
    @JoinColumn(name = "Source_Account", nullable = false)
    private Account sourceAccount;

    private Integer targetAccount;

    @Column(name = "Transaction_Amount", columnDefinition = "DECIMAL(10,2)",nullable = false)
    private Double transactionAmount;

    @Column(name = "Transaction_Remarks", columnDefinition = "VARCHAR(60)")
    private String transactionRemarks;

    @Column(name = "Create_Timestamp", columnDefinition = "DATETIME")
    private LocalDateTime createTimestamp;

    @Column(name = "Update_Timestamp", columnDefinition = "DATETIME")
    private LocalDateTime updateTimestamp;

    @Column(name = "Updated_By", columnDefinition = "VARCHAR(40)",nullable = false)
    private String updatedBy;

}
