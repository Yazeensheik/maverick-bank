package com.wipro.maverick_bank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.maverick_bank.entity.CustomerLoan;

@Repository
public interface CustomerLoanRepository extends JpaRepository<CustomerLoan, Long> {
	
	List<CustomerLoan> findByCustomer_Id(Long userId);
	List<CustomerLoan> findByStatus(String status);

}
