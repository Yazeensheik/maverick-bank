package com.wipro.maverick_bank.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="customer_loans")
public class CustomerLoan {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long customerLoanId;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User customer;
	
	@ManyToOne
	@JoinColumn(name="loan_id")
	private Loan loan;
	
	private double disbursedAmount; //GIVEN AMOUNT
	private double outstandingAmount; //REMAINING AMOUNT
	private String status; //ACTIVE, CLOSED
	
}
