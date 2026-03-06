package com.wipro.maverick_bank.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatementDTO {

    private Long statementId;

    @NotNull(message = "Account ID is required")
    private Long accountId;

    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;

    @NotNull(message = "End date is required")
    private LocalDateTime endDate;

    private LocalDateTime generatedDate;

}