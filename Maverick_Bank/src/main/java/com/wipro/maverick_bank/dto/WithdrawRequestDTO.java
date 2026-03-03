package com.wipro.maverick_bank.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawRequestDTO {

	@NotNull(message = "Account ID is required")
    private Long accountId;
	@NotNull(message = "Amount is required")
    private Double amount;
}
