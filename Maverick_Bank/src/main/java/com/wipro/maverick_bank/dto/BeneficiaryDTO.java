package com.wipro.maverick_bank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BeneficiaryDTO {

	private String beneficiaryName;
	private String accountNumber;
	private String bankName;
	private String branchName;
	private String ifscCode;

	private Long accountId;
}