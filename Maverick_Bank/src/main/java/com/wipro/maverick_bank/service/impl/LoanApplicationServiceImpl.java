package com.wipro.maverick_bank.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wipro.maverick_bank.dto.LoanApplicationDTO;
import com.wipro.maverick_bank.dto.LoanApprovalDTO;
import com.wipro.maverick_bank.entity.Loan;
import com.wipro.maverick_bank.entity.LoanApplication;
import com.wipro.maverick_bank.entity.User;
import com.wipro.maverick_bank.repository.LoanApplicationRepository;
import com.wipro.maverick_bank.repository.LoanRepository;
import com.wipro.maverick_bank.repository.UserRepository;
import com.wipro.maverick_bank.service.interfaces.LoanApplicationService;

@Service
public class LoanApplicationServiceImpl implements LoanApplicationService {
	
	private final LoanApplicationRepository loanApplicationRepository;
	private final LoanRepository loanRepository;
	private final UserRepository userRepository;

	public LoanApplicationServiceImpl(
			LoanApplicationRepository loanApplicationRepository,
			LoanRepository loanRepository,
			UserRepository userRepository) {
		this.loanApplicationRepository = loanApplicationRepository;
		this.loanRepository = loanRepository;
		this.userRepository = userRepository;
	}

	@Override
	public LoanApplicationDTO applyForLoan(Long userID, LoanApplicationDTO dto) {

		Loan loan = loanRepository.findById(dto.getLoanId())
				.orElseThrow(() -> new RuntimeException("Loan not found"));

		User user = userRepository.findById(userID)
				.orElseThrow(() -> new RuntimeException("User not found"));

		LoanApplication application = new LoanApplication(
				null, //applicationID
				loan,
				user,
				dto.getAmount(),
				dto.getPurpose(),
				"PENDING",
				LocalDate.now(),
				null, //approvedDate
				null //remarks
		);

		loanApplicationRepository.save(application);

		return dto;
	}

	@Override
	public LoanApplicationDTO getApplicationById(Long applicationId) {

		LoanApplication application = loanApplicationRepository.findById(applicationId)
				.orElseThrow(() -> new RuntimeException("Loan application not found"));

		return new LoanApplicationDTO(
				application.getLoan().getLoanId(),
				application.getAmount(),
				application.getPurpose()
		);
	}

	@Override
	public List<LoanApplicationDTO> getAllApplications() {

		return loanApplicationRepository.findAll()
				.stream()
				.map(app -> new LoanApplicationDTO(
						app.getLoan().getLoanId(),
						app.getAmount(),
						app.getPurpose()
				))
				.collect(Collectors.toList());
	}

	@Override
	public LoanApprovalDTO approveOrRejectLoan(Long applicationId, LoanApprovalDTO approvalDTO) {

		LoanApplication application = loanApplicationRepository.findById(applicationId)
				.orElseThrow(() -> new RuntimeException("Loan application not found"));

		application.setStatus(approvalDTO.getStatus());
		application.setRemarks(approvalDTO.getRemarks());
		application.setApprovedDate(LocalDate.now());

		loanApplicationRepository.save(application);

		return approvalDTO;
	}

}
