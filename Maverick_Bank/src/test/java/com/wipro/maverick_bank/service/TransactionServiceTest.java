package com.wipro.maverick_bank.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wipro.maverick_bank.dto.TransactionDTO;

@SpringBootTest
public class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Test
    void testDeposit() {

        TransactionDTO dto = new TransactionDTO();
        dto.setAmount(5000.0);
        dto.setTransactionType("DEPOSIT");
        dto.setAccountId(101L);

        TransactionDTO result = transactionService.deposit(dto);

        assertEquals(5000.0, result.getAmount());
        assertEquals("DEPOSIT", result.getTransactionType());

    }
}