package com.wipro.maverick_bank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.maverick_bank.entity.Statement;

@Repository
public interface StatementRepository extends JpaRepository<Statement, Long> {
	
	List<Statement> findByAccountId(Long accountId);
	
}
