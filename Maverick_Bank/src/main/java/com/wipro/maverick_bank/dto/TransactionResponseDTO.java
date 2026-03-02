package com.wipro.maverick_bank.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponseDTO {

    private Long transactionId;
    @NotNull(message = "From Account ID is required")
    private Long fromAccountId;
    @NotNull(message = "To Account ID is required")
    private Long toAccountId;
    private Double amount;
    private String transactionType;
    private String status;
    private String referenceNumber;
    private LocalDateTime transactionDate;
}