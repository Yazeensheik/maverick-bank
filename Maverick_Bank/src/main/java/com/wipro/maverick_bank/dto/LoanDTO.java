package com.wipro.maverick_bank.dto;

import jakarta.validation.constraints.DecimalMin;
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
public class LoanDTO {

	private Long loanId;
	
	@NotBlank(message = "Loan type is required")
	private String LoanType;
	
	@NotNull(message = "Interest rate is required")
    @DecimalMin(value = "0.1", message = "Interest rate must be greater than 0")
	private double interestRate;
	
	@NotNull(message = "Minimum amount is required")
    @Min(value = 1000, message = "Minimum amount must be at least 1000")
	private double minAmount;
	
	@NotNull(message = "Maximum amount is required")
    @Min(value = 1000, message = "Maximum amount must be at least 1000")
	private double maxAmount;
	
	@NotNull(message = "Tenure is required")
    @Min(value = 1, message = "Tenure must be at least 1 year")
	private int tenureInMonths;
}
