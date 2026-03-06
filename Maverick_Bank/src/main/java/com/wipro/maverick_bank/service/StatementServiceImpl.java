package com.wipro.maverick_bank.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wipro.maverick_bank.dto.StatementDTO;
import com.wipro.maverick_bank.dto.TransactionDTO;
import com.wipro.maverick_bank.entity.Transaction;
import com.wipro.maverick_bank.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatementServiceImpl implements StatementService {

	private final TransactionRepository transactionRepository;

	@Override
	public List<TransactionDTO> generateStatement(StatementDTO dto) {

		List<Transaction> transactions = transactionRepository.findByAccountAccountIdAndTransactionDateBetween(
				dto.getAccountId(), dto.getStartDate(), dto.getEndDate());

		return transactions.stream()
				.map(t -> new TransactionDTO(t.getTransactionId(), t.getAmount(), t.getTransactionType(),
						t.getTransactionDate(), t.getReferenceNumber(), t.getAccount().getAccountId()))
				.collect(Collectors.toList());
	}
}