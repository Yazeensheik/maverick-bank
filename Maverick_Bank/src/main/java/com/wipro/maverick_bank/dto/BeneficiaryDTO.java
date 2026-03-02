package com.wipro.maverick_bank.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BeneficiaryDTO {

	@NotBlank(message = "Beneficiary name is required")
	@Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
	private String beneficiaryName;

	@NotBlank(message = "Account number is required")
	@Size(min = 8, max = 20, message = "Account number must be between 8 and 20 characters")
	private String accountNumber;

	@NotBlank(message = "Bank name is required")
	private String bankName;

	@NotBlank(message = "Branch name is required")
	private String branchName;

	@NotBlank(message = "IFSC code is required")
	@Size(min = 5, max = 15, message = "Invalid IFSC format")
	private String ifscCode;

	@NotNull(message = "Account ID is required")
	private Long accountId;
}