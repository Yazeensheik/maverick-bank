package com.wipro.maverick_bank.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationDTO {

	@NotNull(message = "Loan ID is required")
	private Long loanId;
	
	@NotNull(message = "Amount is required")
	@Min(value = 1000, message = "Loan amount must be at least 1000")
	private double amount;
	
	@NotBlank(message = "Purpose is required")
	private String purpose;
}
