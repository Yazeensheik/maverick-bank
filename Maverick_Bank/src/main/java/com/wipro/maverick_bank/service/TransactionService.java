package com.wipro.maverick_bank.service;

import java.util.List;

import com.wipro.maverick_bank.dto.DepositRequestDTO;
import com.wipro.maverick_bank.dto.TransactionResponseDTO;
import com.wipro.maverick_bank.dto.TransferRequestDTO;
import com.wipro.maverick_bank.dto.WithdrawRequestDTO;

public interface TransactionService {
	
	TransactionResponseDTO deposit(DepositRequestDTO request);

    TransactionResponseDTO withdraw(WithdrawRequestDTO request);

    TransactionResponseDTO transfer(TransferRequestDTO request);

    List<TransactionResponseDTO> getAllTransactions(Long accountId);

    List<TransactionResponseDTO> getLast10Transactions(Long accountId);
    
}