package com.wipro.maverick_bank.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wipro.maverick_bank.dto.LoanDTO;
import com.wipro.maverick_bank.entity.Loan;
import com.wipro.maverick_bank.repository.LoanRepository;
import com.wipro.maverick_bank.service.LoanService;

@Service
public class LoanServiceImpl implements LoanService {
	
	private final LoanRepository loanRepository;
	
	public LoanServiceImpl(LoanRepository loanRepository) {
		this.loanRepository= loanRepository;
	}
	
	@Override
	public LoanDTO createLoan(LoanDTO loanDTO) {
		Loan loan=new Loan(
				loanDTO.getLoanId(),
				loanDTO.getLoanType(),
				loanDTO.getInterestRate(),
				loanDTO.getMinAmount(),
				loanDTO.getMaxAmount(),
				loanDTO.getTenureInMonths(), null
		);
		
		Loan savedLoan=loanRepository.save(loan);
		
		return new LoanDTO(
				savedLoan.getLoanId(),
				savedLoan.getLoanType(),
				savedLoan.getInterestRate(),
				savedLoan.getMinAmount(),
				savedLoan.getMaxAmount(),
				savedLoan.getTenureInMonths()
		);
	}

	@Override
	public LoanDTO getLoanById(Long loanId) {
		Loan loan=loanRepository.findById(loanId).orElseThrow(()->new RuntimeException("Loan not found."));
		
		return new LoanDTO(
				loan.getLoanId(),
				loan.getLoanType(),
				loan.getInterestRate(),
				loan.getMinAmount(),
				loan.getMaxAmount(),
				loan.getTenureInMonths());
		
	}
	
	
	@Override
	public List<LoanDTO> getAllLoans(){
		return loanRepository.findAll().stream().map(loan->new LoanDTO(
				loan.getLoanId(),
				loan.getLoanType(),
				loan.getInterestRate(),
				loan.getMinAmount(),
				loan.getMaxAmount(),
				loan.getTenureInMonths()
			)).collect(Collectors.toList());
	}
	
	@Override
	public LoanDTO updateLoan(Long loanId, LoanDTO loanDTO) {
		Loan loan=loanRepository.findById(loanId).orElseThrow(()->new RuntimeException("Loan not found"));
		
		loan.setLoanType(loanDTO.getLoanType());
		loan.setInterestRate(loanDTO.getInterestRate());
		loan.setMinAmount(loan.getMinAmount());
		loan.setMaxAmount(loanDTO.getMaxAmount());
		loan.setTenureInMonths(loanDTO.getTenureInMonths());
		
		Loan updatedLoan= loanRepository.save(loan);
		
		return new LoanDTO(
				updatedLoan.getLoanId(),
				updatedLoan.getLoanType(),
				updatedLoan.getInterestRate(),
				updatedLoan.getMinAmount(),
				updatedLoan.getMaxAmount(),
				updatedLoan.getTenureInMonths());
		
		
	}
	
	@Override
	public void deleteLoan(Long loanId) {
		loanRepository.deleteById(loanId);
	}

	
}
