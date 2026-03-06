package com.wipro.maverick_bank.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.maverick_bank.entity.Account;
import com.wipro.maverick_bank.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	List<Transaction> findByAccountAccountId(Account account);
	
    List<Transaction> findByAccountAccountIdOrderByTransactionDateDesc(Account account);

    List<Transaction> findByAccountAccountIdAndTransactionDateBetween(
            Account account,
            LocalDateTime startDate,
            LocalDateTime endDate
    );

}