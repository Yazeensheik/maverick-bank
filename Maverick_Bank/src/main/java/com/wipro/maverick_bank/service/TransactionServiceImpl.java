package com.wipro.maverick_bank.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wipro.maverick_bank.dto.TransactionDTO;
import com.wipro.maverick_bank.entity.Transaction;
import com.wipro.maverick_bank.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    // Calculate balance from transactions
    private double calculateBalance(Long accountId) {

        List<Transaction> transactions = transactionRepository.findByAccountId(accountId);

        double balance = 0;

        for (Transaction t : transactions) {

            if ("DEPOSIT".equals(t.getTransactionType())) {
                balance += t.getAmount();
            } 
            else if ("WITHDRAW".equals(t.getTransactionType()) ||
                     "TRANSFER".equals(t.getTransactionType())) {
                balance -= t.getAmount();
            }

        }

        return balance;
    }

    // Deposit Money
    @Override
    public TransactionDTO deposit(TransactionDTO dto) {

        Transaction transaction = new Transaction();

        transaction.setAmount(dto.getAmount());
        transaction.setTransactionType("DEPOSIT");
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setReferenceNumber(UUID.randomUUID().toString());
        transaction.setAccountId(dto.getAccountId());

        transactionRepository.save(transaction);

        dto.setTransactionId(transaction.getTransactionId());
        dto.setTransactionDate(transaction.getTransactionDate());
        dto.setReferenceNumber(transaction.getReferenceNumber());

        return dto;
    }

    // Withdraw Money
    @Override
    public TransactionDTO withdraw(TransactionDTO dto) {

        double balance = calculateBalance(dto.getAccountId());

        if (balance < dto.getAmount()) {
            throw new RuntimeException("Insufficient Balance");
        }

        Transaction transaction = new Transaction();

        transaction.setAmount(dto.getAmount());
        transaction.setTransactionType("WITHDRAW");
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setReferenceNumber(UUID.randomUUID().toString());
        transaction.setAccountId(dto.getAccountId());

        transactionRepository.save(transaction);

        dto.setTransactionId(transaction.getTransactionId());
        dto.setTransactionDate(transaction.getTransactionDate());
        dto.setReferenceNumber(transaction.getReferenceNumber());

        return dto;
    }

    // Transfer Money
    @Override
    public TransactionDTO transfer(TransactionDTO dto) {

        double balance = calculateBalance(dto.getAccountId());

        if (balance < dto.getAmount()) {
            throw new RuntimeException("Insufficient Balance");
        }

        Transaction transaction = new Transaction();

        transaction.setAmount(dto.getAmount());
        transaction.setTransactionType("TRANSFER");
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setReferenceNumber(UUID.randomUUID().toString());
        transaction.setAccountId(dto.getAccountId());

        transactionRepository.save(transaction);

        dto.setTransactionId(transaction.getTransactionId());
        dto.setTransactionDate(transaction.getTransactionDate());
        dto.setReferenceNumber(transaction.getReferenceNumber());

        return dto;
    }
    
    @Override
    public Double getBalance(Long accountId) {
        return calculateBalance(accountId);
    }

    // Get Transaction History
    @Override
    public List<TransactionDTO> getTransactionsByAccount(Long accountId) {

        List<Transaction> transactions =
                transactionRepository.findByAccountIdOrderByTransactionDateDesc(accountId);

        return transactions.stream().map(t -> new TransactionDTO(
                t.getTransactionId(),
                t.getAmount(),
                t.getTransactionType(),
                t.getTransactionDate(),
                t.getReferenceNumber(),
                t.getAccountId()
        )).collect(Collectors.toList());
    }
}