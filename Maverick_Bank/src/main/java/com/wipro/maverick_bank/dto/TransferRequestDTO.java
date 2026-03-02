package com.wipro.maverick_bank.dto;

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
public class TransferRequestDTO {

    private Long fromAccountId;
    private Long toAccountId;
    private Double amount;
}
