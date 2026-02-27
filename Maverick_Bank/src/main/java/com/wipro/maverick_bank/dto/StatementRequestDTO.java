package com.wipro.maverick_bank.dto;

import java.time.LocalDate;

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
public class StatementRequestDTO {

    private Long accountId;
    private LocalDate startDate;
    private LocalDate endDate;
}