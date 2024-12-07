package com.hackathon.MortgageDemo.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "customer")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Customer{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Customer_Id")
	private Long customerId;

	@Column(name = "Customer_Name", columnDefinition = "VARCHAR(40)")
	private String customerName;

	@Column(name = "Date_Of_Birth", columnDefinition = "DATE")
	private LocalDate dateOfBirth;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Account> accounts;

	@Column(name = "Gender", columnDefinition = "CHAR(1)")
	private String gender;

	@Column(name = "Create_Timestamp", columnDefinition = "DATETIME")
	private LocalDateTime createTimestamp;

	@Column(name = "Update_Timestamp", columnDefinition = "DATETIME")
	private LocalDateTime updateTimestamp;

	@Column(name = "Updated_By", columnDefinition = "VARCHAR(40)",nullable = false)
	private String updatedBy;

	@Column(name = "Password", columnDefinition = "VARCHAR(250)",nullable = false)
	private String password;


}
