package com.wipro.maverick_bank.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wipro.maverick_bank.dto.TransactionDTO;
import com.wipro.maverick_bank.entity.Transaction;
import com.wipro.maverick_bank.repository.TransactionRepository;
import com.wipro.maverick_bank.service.StatementService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatementServiceImpl implements StatementService {

    private final TransactionRepository transactionRepository;

    @Override
    public List<TransactionDTO> getLast10Transactions(Long accountId) {

        List<Transaction> transactions =
                transactionRepository.findTop10ByAccount_AccountIdOrderByTransactionDateDesc(accountId);

        return transactions.stream().map(t -> new TransactionDTO(
                t.getTransactionId(),
                t.getAmount(),
                t.getTransactionType(),
                t.getTransactionDate(),
                t.getReferenceNumber(),
                t.getAccount().getAccountId()
        )).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> getTransactionsBetweenDates(
            Long accountId,
            LocalDateTime startDate,
            LocalDateTime endDate) {

        List<Transaction> transactions =
                transactionRepository.findByAccount_AccountIdAndTransactionDateBetween(
                        accountId, startDate, endDate);

        return transactions.stream().map(t -> new TransactionDTO(
                t.getTransactionId(),
                t.getAmount(),
                t.getTransactionType(),
                t.getTransactionDate(),
                t.getReferenceNumber(),
                t.getAccount().getAccountId()
        )).collect(Collectors.toList());
    }
}