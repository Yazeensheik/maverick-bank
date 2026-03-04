package com.wipro.maverick_bank.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.wipro.maverick_bank.dto.DepositRequestDTO;
import com.wipro.maverick_bank.dto.TransactionResponseDTO;
import com.wipro.maverick_bank.dto.TransferRequestDTO;
import com.wipro.maverick_bank.dto.WithdrawRequestDTO;
import com.wipro.maverick_bank.entity.Account;
import com.wipro.maverick_bank.entity.Transaction;
import com.wipro.maverick_bank.repository.AccountRepository;
import com.wipro.maverick_bank.repository.TransactionRepository;
import com.wipro.maverick_bank.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    // ========================= DEPOSIT =========================
    @Override
    public TransactionResponseDTO deposit(DepositRequestDTO request) {

        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Update balance
        account.setBalance(account.getBalance() + request.getAmount());
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setToAccountId(request.getAccountId());
        transaction.setAmount(request.getAmount());
        transaction.setTransactionType("DEPOSIT");
        transaction.setStatus("SUCCESS");
        transaction.setReferenceNumber(UUID.randomUUID().toString());
        transaction.setTransactionDate(LocalDateTime.now());

        transactionRepository.save(transaction);

        return mapToDTO(transaction);
    }

    // ========================= WITHDRAW =========================
    @Override
    public TransactionResponseDTO withdraw(WithdrawRequestDTO request) {

        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance() < request.getAmount()) {
            throw new RuntimeException("Insufficient balance");
        }

        // Update balance
        account.setBalance(account.getBalance() - request.getAmount());
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setFromAccountId(request.getAccountId());
        transaction.setAmount(request.getAmount());
        transaction.setTransactionType("WITHDRAW");
        transaction.setStatus("SUCCESS");
        transaction.setReferenceNumber(UUID.randomUUID().toString());
        transaction.setTransactionDate(LocalDateTime.now());

        transactionRepository.save(transaction);

        return mapToDTO(transaction);
    }

    // ========================= TRANSFER =========================
    @Override
    public TransactionResponseDTO transfer(TransferRequestDTO request) {

        Account fromAccount = accountRepository.findById(request.getFromAccountId())
                .orElseThrow(() -> new RuntimeException("From Account not found"));

        Account toAccount = accountRepository.findById(request.getToAccountId())
                .orElseThrow(() -> new RuntimeException("To Account not found"));

        if (fromAccount.getBalance() < request.getAmount()) {
            throw new RuntimeException("Insufficient balance");
        }

        // Update balances
        fromAccount.setBalance(fromAccount.getBalance() - request.getAmount());
        toAccount.setBalance(toAccount.getBalance() + request.getAmount());

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction transaction = new Transaction();
        transaction.setAccount(fromAccount);
        transaction.setFromAccountId(request.getFromAccountId());
        transaction.setToAccountId(request.getToAccountId());
        transaction.setAmount(request.getAmount());
        transaction.setTransactionType("TRANSFER");
        transaction.setStatus("SUCCESS");
        transaction.setReferenceNumber(UUID.randomUUID().toString());
        transaction.setTransactionDate(LocalDateTime.now());

        transactionRepository.save(transaction);

        return mapToDTO(transaction);
    }

    // ========================= GET ALL =========================
    @Override
    public List<TransactionResponseDTO> getAllTransactions(Long accountId) {

        List<Transaction> sent = transactionRepository.findByFromAccountId(accountId);
        List<Transaction> received = transactionRepository.findByToAccountId(accountId);

        List<Transaction> allTransactions = new ArrayList<>();
        allTransactions.addAll(sent);
        allTransactions.addAll(received);

        allTransactions.sort(Comparator.comparing(Transaction::getTransactionDate).reversed());

        List<TransactionResponseDTO> responseList = new ArrayList<>();

        for (Transaction t : allTransactions) {
            responseList.add(mapToDTO(t));
        }

        return responseList;
    }

    // ========================= LAST 10 =========================
    @Override
    public List<TransactionResponseDTO> getLast10Transactions(Long accountId) {

        List<TransactionResponseDTO> all = getAllTransactions(accountId);

        if (all.size() > 10) {
            return all.subList(0, 10);
        }

        return all;
    }

    // ========================= MAPPER =========================
    private TransactionResponseDTO mapToDTO(Transaction t) {

        TransactionResponseDTO dto = new TransactionResponseDTO();
        dto.setTransactionId(t.getTransactionId());
        dto.setFromAccountId(t.getFromAccountId());
        dto.setToAccountId(t.getToAccountId());
        dto.setAmount(t.getAmount());
        dto.setTransactionType(t.getTransactionType());
        dto.setStatus(t.getStatus());
        dto.setReferenceNumber(t.getReferenceNumber());
        dto.setTransactionDate(t.getTransactionDate());

        return dto;
    }
}