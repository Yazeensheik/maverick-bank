package com.wipro.maverick_bank.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.maverick_bank.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountIdOrderByTransactionDateDesc(Long accountId);

    List<Transaction> findByAccountIdAndTransactionDateBetween(
            Long accountId,
            LocalDateTime startDate,
            LocalDateTime endDate
    );

}