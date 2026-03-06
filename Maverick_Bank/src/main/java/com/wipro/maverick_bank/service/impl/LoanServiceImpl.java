package com.wipro.maverick_bank.service.impl;

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
	public LoanDTO createLoan(LoanDTO dto) {

	    Loan loan = new Loan();

	    loan.setLoanType(dto.getLoanType());
	    loan.setInterestRate(dto.getInterestRate());
	    loan.setMinAmount(dto.getMinAmount());
	    loan.setMaxAmount(dto.getMaxAmount());
	    loan.setTenureInMonths(dto.getTenureInMonths());

	    Loan savedLoan = loanRepository.save(loan);

	    LoanDTO response = new LoanDTO();
	    response.setLoanId(savedLoan.getLoanId());
	    response.setLoanType(savedLoan.getLoanType());
	    response.setInterestRate(savedLoan.getInterestRate());
	    response.setMinAmount(savedLoan.getMinAmount());
	    response.setMaxAmount(savedLoan.getMaxAmount());
	    response.setTenureInMonths(savedLoan.getTenureInMonths());

	    return response;
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
