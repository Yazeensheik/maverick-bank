package com.wipro.maverick_bank.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "statements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Statement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long statementId;

	@Column(nullable = false)
	private Long accountId;

	@Column(nullable = false)
	private LocalDate startDate;

	@Column(nullable = false)
	private LocalDate endDate;

	@Column(nullable = false)
	private Double totalCredit;

	@Column(nullable = false)
	private Double totalDebit;

	@Column(nullable = false)
	private LocalDateTime generatedDate;

	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;
	
}