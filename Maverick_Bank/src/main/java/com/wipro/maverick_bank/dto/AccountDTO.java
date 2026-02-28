package com.wipro.maverick_bank.dto;

import com.wipro.maverick_bank.entity.CustomerProfile;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class AccountDTO {

	private String accountNumber;
	private String accountType;
	private Double balance;
	private String status;
	private Long accountId;
	private Long customerProfileId;

	public AccountDTO() {
	}

	public AccountDTO(String accountNumber, String accountType, Double balance, String status, Long accountId,
			Long customerProfileId) {

		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.balance = balance;
		this.status = status;
		this.accountId = accountId;
		this.customerProfileId = customerProfileId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public Double getBalance() {
		return balance;
	}

	public String getStatus() {
		return status;
	}

	public Long getAccountId() {
		return accountId;
	}

	public Long getCustomerProfileId() {
		return customerProfileId;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public void setCustomerProfileId(Long customerProfileId) {
		this.customerProfileId = customerProfileId;
	}

	@ManyToOne
	@JoinColumn(name = "customer_profile_id")
	private CustomerProfile customerProfile;
}