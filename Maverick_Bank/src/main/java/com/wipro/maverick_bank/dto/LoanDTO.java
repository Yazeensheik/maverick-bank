package com.wipro.maverick_bank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanDTO {

	private Long loanId;
	private String LoanType;
	private double interestRate;
	private double minAmount;
	private double maxAmount;
	private int tenureInMonths;
}
