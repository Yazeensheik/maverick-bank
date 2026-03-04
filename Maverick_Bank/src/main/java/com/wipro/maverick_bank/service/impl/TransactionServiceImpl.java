package com.wipro.maverick_bank.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wipro.maverick_bank.dto.TransactionDTO;
import com.wipro.maverick_bank.entity.Account;
import com.wipro.maverick_bank.entity.Transaction;
import com.wipro.maverick_bank.repository.AccountRepository;
import com.wipro.maverick_bank.repository.TransactionRepository;
import com.wipro.maverick_bank.service.TransactionService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public TransactionDTO deposit(TransactionDTO dto) {

        Account account = accountRepository.findById(dto.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance() + dto.getAmount());

        Transaction transaction = new Transaction();
        transaction.setAmount(dto.getAmount());
        transaction.setTransactionType("DEPOSIT");
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setReferenceNumber(UUID.randomUUID().toString());
        transaction.setAccount(account);

        transactionRepository.save(transaction);
        accountRepository.save(account);

        dto.setTransactionId(transaction.getTransactionId());
        dto.setTransactionDate(transaction.getTransactionDate());
        dto.setReferenceNumber(transaction.getReferenceNumber());

        return dto;
    }

    @Override
    public TransactionDTO withdraw(TransactionDTO dto) {

        Account account = accountRepository.findById(dto.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance() < dto.getAmount()) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance() - dto.getAmount());

        Transaction transaction = new Transaction();
        transaction.setAmount(dto.getAmount());
        transaction.setTransactionType("WITHDRAW");
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setReferenceNumber(UUID.randomUUID().toString());
        transaction.setAccount(account);

        transactionRepository.save(transaction);
        accountRepository.save(account);

        dto.setTransactionId(transaction.getTransactionId());
        dto.setTransactionDate(transaction.getTransactionDate());
        dto.setReferenceNumber(transaction.getReferenceNumber());

        return dto;
    }

    @Override
    public TransactionDTO transfer(TransactionDTO dto) {

        Account account = accountRepository.findById(dto.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance() < dto.getAmount()) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance() - dto.getAmount());

        Transaction transaction = new Transaction();
        transaction.setAmount(dto.getAmount());
        transaction.setTransactionType("TRANSFER");
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setReferenceNumber(UUID.randomUUID().toString());
        transaction.setAccount(account);

        transactionRepository.save(transaction);
        accountRepository.save(account);

        dto.setTransactionId(transaction.getTransactionId());
        dto.setTransactionDate(transaction.getTransactionDate());
        dto.setReferenceNumber(transaction.getReferenceNumber());

        return dto;
    }

    @Override
    public List<TransactionDTO> getTransactionsByAccount(Long accountId) {

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

}