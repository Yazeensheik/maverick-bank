package com.wipro.maverick_bank.service;

import java.time.LocalDateTime;
import java.util.List;

import com.wipro.maverick_bank.dto.TransactionDTO;

public interface StatementService {

    List<TransactionDTO> getLast10Transactions(Long accountId);

    List<TransactionDTO> getTransactionsBetweenDates(Long accountId, LocalDateTime startDate, LocalDateTime endDate);

}