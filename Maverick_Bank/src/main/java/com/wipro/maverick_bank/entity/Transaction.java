package com.wipro.maverick_bank.entity;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String transactionType; 
    // DEPOSIT / WITHDRAW / TRANSFER

    @Column(nullable = false)
    private LocalDateTime transactionDate = LocalDateTime.now();

    @Column(unique = true)
    private String referenceNumber;

    // Relationship with Account
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

}