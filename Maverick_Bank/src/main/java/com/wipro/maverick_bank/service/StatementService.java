package com.wipro.maverick_bank.service;

import java.util.List;

import com.wipro.maverick_bank.dto.StatementDTO;
import com.wipro.maverick_bank.dto.TransactionDTO;

public interface StatementService {

    List<TransactionDTO> generateStatement(StatementDTO statementDTO);

}