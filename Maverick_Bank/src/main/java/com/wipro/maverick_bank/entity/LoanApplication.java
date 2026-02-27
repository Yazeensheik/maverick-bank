package com.wipro.maverick_bank.entity;

import java.time.LocalDate;

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
@Table(name="loan_applications")
public class LoanApplication {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long applicationId;
	
	@ManyToOne
	@JoinColumn(name="loan_id", nullable=false)
	private Loan loan;
	
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private User customer;
	
	private double amount;
	private String purpose;
	private String status; //PENDING, APPROVED, REJECTED
	private LocalDate appliedDate;
	private LocalDate approvedDate;
	private String remarks;
	
}
