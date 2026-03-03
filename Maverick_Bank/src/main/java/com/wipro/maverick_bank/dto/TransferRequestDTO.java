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
public class TransferRequestDTO {

	@NotNull(message = "From Account ID is required")
    private Long fromAccountId;
	@NotNull(message = "To Account ID is required")
    private Long toAccountId;
    private Double amount;
}
