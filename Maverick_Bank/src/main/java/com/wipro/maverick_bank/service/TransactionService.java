package com.wipro.maverick_bank.service;

import java.util.List;

import com.wipro.maverick_bank.dto.TransactionDTO;

public interface TransactionService {

    TransactionDTO deposit(TransactionDTO transactionDTO);

    TransactionDTO withdraw(TransactionDTO transactionDTO);

    TransactionDTO transfer(TransactionDTO transactionDTO);

    List<TransactionDTO> getTransactionsByAccount(Long accountId);

}