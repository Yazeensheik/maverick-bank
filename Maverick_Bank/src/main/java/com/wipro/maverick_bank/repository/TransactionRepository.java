package com.wipro.maverick_bank.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.maverick_bank.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Last 10 transactions
    List<Transaction> findTop10ByAccount_AccountIdOrderByTransactionDateDesc(Long accountId);

    // Transactions between two dates
    List<Transaction> findByAccount_AccountIdAndTransactionDateBetween(Long accountId,LocalDateTime startDate,LocalDateTime endDate);

}