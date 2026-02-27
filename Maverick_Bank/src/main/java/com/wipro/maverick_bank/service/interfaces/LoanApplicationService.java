package com.wipro.maverick_bank.service.interfaces;

import java.util.List;

import com.wipro.maverick_bank.dto.LoanApplicationDTO;
import com.wipro.maverick_bank.dto.LoanApprovalDTO;

public interface LoanApplicationService {

	LoanApplicationDTO applyForLoan(Long userID, LoanApplicationDTO loanApplicationDTO);
	LoanApplicationDTO getApplicationById(Long applicationId);
	List<LoanApplicationDTO> getAllApplications();
	LoanApprovalDTO approveOrRejectLoan(Long applicationId, LoanApprovalDTO approvalDTO);
}
