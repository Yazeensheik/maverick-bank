package com.wipro.maverick_bank.service;

import java.util.List;

import com.wipro.maverick_bank.dto.StatementRequestDTO;
import com.wipro.maverick_bank.dto.StatementResponseDTO;

public interface StatementService {
	
	StatementResponseDTO generateStatement(StatementRequestDTO request);

    List<StatementResponseDTO> getStatements(Long accountId);
    
}