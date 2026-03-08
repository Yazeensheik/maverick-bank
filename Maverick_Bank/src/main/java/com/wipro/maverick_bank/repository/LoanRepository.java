package com.wipro.maverick_bank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.maverick_bank.entity.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
	Optional<Loan> findByLoanType(String loanType);
}
