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
	@NotBlank(message = "Beneficiary name is required")
	private String beneficiaryName;

	@NotBlank(message = "Account number is required")
	@Size(min = 8, max = 20)
	private String accountNumber;

	@NotBlank(message = "Bank name is required")
	private String bankName;

	@NotBlank(message = "Branch name is required")
	private String branchName;

	@NotBlank(message = "IFSC code is required")
	@Size(min = 5, max = 15)
	private String ifscCode;
	
	@ManyToOne
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;

}