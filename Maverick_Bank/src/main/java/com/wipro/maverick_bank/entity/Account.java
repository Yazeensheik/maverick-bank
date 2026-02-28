package com.wipro.maverick_bank.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long accountId;

	@Column(nullable = false, unique = true, length = 20)
	private String accountNumber;

	@Column(nullable = false, length = 20)
	private String accountType;

	@Column(nullable = false)
	private Double balance = 0.0;

	@Column(nullable = false, length = 20)
	private String status;

	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private List<Beneficiary> beneficiaries;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_profile_id", nullable = false)
	private CustomerProfile customerProfile;

	@OneToMany(mappedBy = "account")
	private List<Transaction> transactions;

	@OneToMany(mappedBy = "account")
	private List<Statement> statements;

}