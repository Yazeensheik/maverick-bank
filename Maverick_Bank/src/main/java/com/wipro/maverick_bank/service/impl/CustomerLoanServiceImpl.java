package com.wipro.maverick_bank.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wipro.maverick_bank.entity.CustomerLoan;
import com.wipro.maverick_bank.entity.LoanApplication;
import com.wipro.maverick_bank.repository.CustomerLoanRepository;
import com.wipro.maverick_bank.repository.LoanApplicationRepository;
import com.wipro.maverick_bank.service.interfaces.CustomerLoanService;

@Service
public class CustomerLoanServiceImpl implements CustomerLoanService{

	
	private final CustomerLoanRepository customerLoanRepository;
	private final LoanApplicationRepository loanApplicationRepository;

	public CustomerLoanServiceImpl(CustomerLoanRepository customerLoanRepository,
								   LoanApplicationRepository loanApplicationRepository) {
		this.customerLoanRepository = customerLoanRepository;
		this.loanApplicationRepository = loanApplicationRepository;
	}

	@Override
	public CustomerLoan createCustomerLoan(Long applicationId) {

		LoanApplication application = loanApplicationRepository.findById(applicationId)
				.orElseThrow(() -> new RuntimeException("Loan application not found"));

		CustomerLoan customerLoan = new CustomerLoan(
				null,
				application.getCustomer(),
				application.getLoan(),
				application.getAmount(),
				application.getAmount(),
				"ACTIVE"
		);

		return customerLoanRepository.save(customerLoan);
	}

	@Override
	public CustomerLoan getCustomerLoanById(Long customerLoanId) {
		return customerLoanRepository.findById(customerLoanId)
				.orElseThrow(() -> new RuntimeException("Customer loan not found"));
	}

	@Override
	public List<CustomerLoan> getAllCustomerLoans() {
		return customerLoanRepository.findAll();
	}

	@Override
	public CustomerLoan updateOutstandingAmount(Long customerLoanId, double amount) {

		CustomerLoan loan = customerLoanRepository.findById(customerLoanId)
				.orElseThrow(() -> new RuntimeException("Customer loan not found"));

		loan.setOutstandingAmount(amount);
		return customerLoanRepository.save(loan);
	}

	@Override
	public CustomerLoan closeLoan(Long customerLoanId) {

		CustomerLoan loan = customerLoanRepository.findById(customerLoanId)
				.orElseThrow(() -> new RuntimeException("Customer loan not found"));

		loan.setStatus("CLOSED");
		return customerLoanRepository.save(loan);
	}
}
