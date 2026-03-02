package com.wipro.maverick_bank.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatementRequestDTO {

	@NotNull(message = "Account ID is required")
    private Long accountId;
    private LocalDate startDate;
    private LocalDate endDate;
}