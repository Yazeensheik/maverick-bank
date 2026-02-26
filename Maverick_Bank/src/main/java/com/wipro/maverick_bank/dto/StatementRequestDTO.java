package com.wipro.maverick_bank.dto;

import java.time.LocalDate;

public class StatementRequestDTO {
	
	private Long accountId;
    private LocalDate startDate;
    private LocalDate endDate;

    public StatementRequestDTO() {
    	
    }

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
    
    
    
}
