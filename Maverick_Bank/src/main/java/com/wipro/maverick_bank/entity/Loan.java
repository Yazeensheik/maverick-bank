package com.wipro.maverick_bank.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name="loans")
public class Loan {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long loanId;
	
	@Column(nullable=false)
	private String loanType;  //HOME, PERSONAL, EDUCATION
	private double interestRate;
	private double minAmount;
	private double maxAmount;
	private int tenureInMonths;
	
	@OneToMany(mappedBy="loan")
	private List<LoanApplication> loanApplication;
}