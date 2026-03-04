package com.wipro.maverick_bank.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

    private Long transactionId;

    @NotNull(message = "Amount is required")
    private Double amount;

    @NotBlank(message = "Transaction type is required")
    private String transactionType;

    private LocalDateTime transactionDate;

    private String referenceNumber;

    @NotNull(message = "Account ID is required")
    private Long accountId;

}