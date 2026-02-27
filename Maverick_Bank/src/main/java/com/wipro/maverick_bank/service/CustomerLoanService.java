package com.wipro.maverick_bank.service;

import java.util.List;

import com.wipro.maverick_bank.entity.CustomerLoan;

public interface CustomerLoanService {
	
	CustomerLoan createCustomerLoan(Long applicationId);
	CustomerLoan getCustomerLoanById(Long customerLoanId);
	List<CustomerLoan> getAllCustomerLoans();
	CustomerLoan updateOutstandingAmount(Long customerLoanId, double amount);
	CustomerLoan closeLoan(Long customerLoanId);

}
