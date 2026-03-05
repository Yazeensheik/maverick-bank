package com.wipro.maverick_bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.maverick_bank.entity.Statement;

public interface StatementRepository extends JpaRepository<Statement, Long> {

}