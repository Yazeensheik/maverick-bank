package com.wipro.maverick_bank.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "beneficiaries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Beneficiary {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long beneficiaryId;

	@NotBlank(message = "Account number is required")
	@Size(min = 8, max = 20)
	@Column(nullable = false, unique = true)
	private String accountNumber;

	@NotBlank(message = "Account type is required")
	@Column(nullable = false)
	private String accountType;


	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull(message = "Balance is required")
	@PositiveOrZero(message = "Balance cannot be negative")
	@Column(nullable = false)
	private Double balance;

	@NotBlank(message = "Status is required")
	@Column(nullable = false)
	private String status;
	
	@ManyToOne
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;

}