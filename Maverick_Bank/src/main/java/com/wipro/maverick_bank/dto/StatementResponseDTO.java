package com.wipro.maverick_bank.dto;

import java.time.LocalDate;
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
public class StatementResponseDTO {

    private Long statementId;
    @NotNull(message = "Account ID is required")
    private Long accountId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double totalCredit;
    private Double totalDebit;
    private LocalDateTime generatedDate;
}
