package com.wipro.maverick_bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.maverick_bank.entity.Statement;

import java.util.List;

@Repository
public interface StatementRepository extends JpaRepository<Statement, Long> {

    List<Statement> findByAccount_AccountId(Long accountId);
}