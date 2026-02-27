package com.wipro.maverick_bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.maverick_bank.entity.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

}
