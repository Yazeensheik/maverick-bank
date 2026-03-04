package com.wipro.maverick_bank.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wipro.maverick_bank.dto.DepositRequestDTO;
import com.wipro.maverick_bank.dto.StatementRequestDTO;
import com.wipro.maverick_bank.dto.StatementResponseDTO;
import com.wipro.maverick_bank.dto.WithdrawRequestDTO;

@SpringBootTest
public class StatementServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private StatementService statementService;

    @Test
    public void testGenerateStatement() {

        // Create a deposit (credit)
        DepositRequestDTO deposit = new DepositRequestDTO();
        deposit.setAccountId(1L);
        deposit.setAmount(2000.0);
        transactionService.deposit(deposit);

        // Create a withdraw (debit)
        WithdrawRequestDTO withdraw = new WithdrawRequestDTO();
        withdraw.setAccountId(101L);
        withdraw.setAmount(500.0);
        transactionService.withdraw(withdraw);

        // Generate statement
        StatementRequestDTO request = new StatementRequestDTO();
        request.setAccountId(101L);
        request.setStartDate(LocalDate.now().minusDays(1));
        request.setEndDate(LocalDate.now().plusDays(1));

        StatementResponseDTO response = statementService.generateStatement(request);

        // Assertions
        assertNotNull(response);
        assertEquals(2000.0, response.getTotalCredit());
        assertEquals(500.0, response.getTotalDebit());
    }
}