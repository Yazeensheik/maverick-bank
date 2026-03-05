package com.wipro.maverick_bank.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wipro.maverick_bank.dto.TransactionDTO;
import com.wipro.maverick_bank.entity.Account;
import com.wipro.maverick_bank.entity.Transaction;
import com.wipro.maverick_bank.exception.ResourceNotFoundException;
import com.wipro.maverick_bank.repository.AccountRepository;
import com.wipro.maverick_bank.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

	private final TransactionRepository transactionRepository;
	private final AccountRepository accountRepository;

	@Override
	public TransactionDTO deposit(TransactionDTO dto) {

		Transaction transaction = new Transaction();

		transaction.setAmount(dto.getAmount());
		transaction.setTransactionType("DEPOSIT");
		transaction.setTransactionDate(LocalDateTime.now());
		transaction.setReferenceNumber(UUID.randomUUID().toString());
		Account account = accountRepository.findById(dto.getAccountId())
				.orElseThrow(() -> new ResourceNotFoundException("Account not found"));

		transaction.setAccount(account);

		transactionRepository.save(transaction);

		dto.setTransactionId(transaction.getTransactionId());
		dto.setTransactionDate(transaction.getTransactionDate());
		dto.setReferenceNumber(transaction.getReferenceNumber());

		return dto;
	}

	@Override
	public TransactionDTO withdraw(TransactionDTO dto) {

		Transaction transaction = new Transaction();

		transaction.setAmount(dto.getAmount());
		transaction.setTransactionType("WITHDRAW");
		transaction.setTransactionDate(LocalDateTime.now());
		transaction.setReferenceNumber(UUID.randomUUID().toString());
		Account account = accountRepository.findById(dto.getAccountId())
				.orElseThrow(() -> new ResourceNotFoundException("Account not found"));

		transaction.setAccount(account);

		transactionRepository.save(transaction);

		dto.setTransactionId(transaction.getTransactionId());
		dto.setTransactionDate(transaction.getTransactionDate());
		dto.setReferenceNumber(transaction.getReferenceNumber());

		return dto;
	}

	@Override
	public TransactionDTO transfer(TransactionDTO dto) {

		Transaction transaction = new Transaction();

		transaction.setAmount(dto.getAmount());
		transaction.setTransactionType("TRANSFER");
		transaction.setTransactionDate(LocalDateTime.now());
		transaction.setReferenceNumber(UUID.randomUUID().toString());
		Account account = accountRepository.findById(dto.getAccountId())
				.orElseThrow(() -> new RuntimeException("Account not found"));

		transaction.setAccount(account);

		transactionRepository.save(transaction);

		dto.setTransactionId(transaction.getTransactionId());
		dto.setTransactionDate(transaction.getTransactionDate());
		dto.setReferenceNumber(transaction.getReferenceNumber());

		return dto;
	}

	@Override
	public List<TransactionDTO> getTransactionsByAccount(Long accountId) {

		List<Transaction> transactions = transactionRepository
				.findByAccountAccountIdOrderByTransactionDateDesc(accountId);

		return transactions.stream()
				.map(t -> new TransactionDTO(t.getTransactionId(), t.getAmount(), t.getTransactionType(),
						t.getTransactionDate(), t.getReferenceNumber(), t.getAccount().getAccountId()))
				.collect(Collectors.toList());
	}
}