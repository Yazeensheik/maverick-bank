package com.wipro.maverick_bank.service;

import java.util.List;

import com.wipro.maverick_bank.dto.LoanApplicationDTO;

public interface LoanApplicationService {

	LoanApplicationDTO applyLoan(LoanApplicationDTO dto);

    LoanApplicationDTO getApplicationById(Long id);
    
    List<LoanApplicationDTO> getAllApplications();
    
    void approveLoan(Long id);
    
    void rejectLoan(Long id);
}
