package com.wipro.maverick_bank.dto;

public class LoanApplicationDTO {

    private Long applicationId;
    private Long loanId;
    private Double amount;
    private String purpose;
    private String status;

    public LoanApplicationDTO() {}

    public LoanApplicationDTO(Long applicationId, Long loanId, Double amount, String purpose, String status) {
        this.applicationId = applicationId;
        this.loanId = loanId;
        this.amount = amount;
        this.purpose = purpose;
        this.status=status;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
    
}