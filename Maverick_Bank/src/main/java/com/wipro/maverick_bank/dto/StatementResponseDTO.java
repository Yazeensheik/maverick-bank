package com.wipro.maverick_bank.dto;

import java.time.LocalDate;
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
public class StatementResponseDTO {

    private Long statementId;
    private Long accountId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double totalCredit;
    private Double totalDebit;
    private LocalDateTime generatedDate;
}
