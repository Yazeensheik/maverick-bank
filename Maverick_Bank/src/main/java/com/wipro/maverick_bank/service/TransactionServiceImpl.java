package com.wipro.maverick_bank.service;

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
import com.wipro.maverick_bank.entity.Transaction;
import com.wipro.maverick_bank.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public TransactionResponseDTO deposit(DepositRequestDTO request) {

        Transaction transaction = new Transaction();
        transaction.setToAccountId(request.getAccountId());
        transaction.setAmount(request.getAmount());
        transaction.setTransactionType("DEPOSIT");
        transaction.setStatus("SUCCESS");
        transaction.setReferenceNumber(UUID.randomUUID().toString());
        transaction.setTransactionDate(LocalDateTime.now());

        transactionRepository.save(transaction);

        return mapToDTO(transaction);
    }

    @Override
    public TransactionResponseDTO withdraw(WithdrawRequestDTO request) {

        Transaction transaction = new Transaction();
        transaction.setFromAccountId(request.getAccountId());
        transaction.setAmount(request.getAmount());
        transaction.setTransactionType("WITHDRAW");
        transaction.setStatus("SUCCESS");
        transaction.setReferenceNumber(UUID.randomUUID().toString());
        transaction.setTransactionDate(LocalDateTime.now());

        transactionRepository.save(transaction);

        return mapToDTO(transaction);
    }

    @Override
    public TransactionResponseDTO transfer(TransferRequestDTO request) {

        Transaction transaction = new Transaction();
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

    @Override
    public List<TransactionResponseDTO> getLast10Transactions(Long accountId) {

        List<TransactionResponseDTO> all = getAllTransactions(accountId);

        if (all.size() > 10) {
            return all.subList(0, 10);
        }

        return all;
    }

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