package com.wipro.maverick_bank.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    private Long fromAccountId;

    private Long toAccountId;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false, length = 20)
    private String transactionType; 

    @Column(nullable = false)
    private LocalDateTime transactionDate;

    @Column(nullable = false, unique = true, length = 30)
    private String referenceNumber;

    @Column(nullable = false, length = 10)
    private String status; 
}