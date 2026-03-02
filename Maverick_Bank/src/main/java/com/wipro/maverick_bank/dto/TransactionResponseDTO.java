package com.wipro.maverick_bank.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponseDTO {

    private Long transactionId;
    private Long fromAccountId;
    private Long toAccountId;
    private Double amount;
    private String transactionType;
    private String status;
    private String referenceNumber;
    private LocalDateTime transactionDate;
}