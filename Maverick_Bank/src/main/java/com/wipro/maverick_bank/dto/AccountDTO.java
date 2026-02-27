package com.wipro.maverick_bank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

	private String accountNumber;
	private String accountType;
	private Double balance;
	private String status;

	private Long accountId;
}