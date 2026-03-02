package com.wipro.maverick_bank.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanApprovalDTO {

	private Long applicationId;
	private String status;
	
	@Size(max = 255, message = "Remarks cannot exceed 255 characters")
	private String remarks;
}
