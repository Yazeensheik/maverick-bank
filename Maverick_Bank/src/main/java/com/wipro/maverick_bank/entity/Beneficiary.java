package com.wipro.maverick_bank.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

	@Column(nullable = false)
	private String beneficiaryName;

	@Column(nullable = false)
	private String accountNumber;

	private String bankName;
	private String branchName;
	private String ifscCode;

	@ManyToOne
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;
}