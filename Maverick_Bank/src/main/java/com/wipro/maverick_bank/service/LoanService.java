package com.wipro.maverick_bank.service;

import java.util.List;

import com.wipro.maverick_bank.dto.LoanDTO;

public interface LoanService {

	LoanDTO createLoan(LoanDTO loanDTO);
	LoanDTO getLoanById(Long loanId);
	List<LoanDTO> getAllLoans();
	LoanDTO updateLoan(Long loanId, LoanDTO loanDTO);
	void deleteLoan(Long loanID);
	
}
