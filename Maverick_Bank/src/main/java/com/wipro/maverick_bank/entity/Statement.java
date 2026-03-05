package com.wipro.maverick_bank.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "statements")
public class Statement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long statementId;

	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;

	private LocalDateTime startDate;

	private LocalDateTime endDate;

	private LocalDateTime generatedDate;

	public Statement() {
	}

	public Statement(Long statementId, Long accountId, LocalDateTime startDate, LocalDateTime endDate,
			LocalDateTime generatedDate) {
		this.statementId = statementId;
		this.account = account;
		this.startDate = startDate;
		this.endDate = endDate;
		this.generatedDate = generatedDate;
	}

	public Long getStatementId() {
		return statementId;
	}

	public void setStatementId(Long statementId) {
		this.statementId = statementId;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public LocalDateTime getGeneratedDate() {
		return generatedDate;
	}

	public void setGeneratedDate(LocalDateTime generatedDate) {
		this.generatedDate = generatedDate;
	}
}