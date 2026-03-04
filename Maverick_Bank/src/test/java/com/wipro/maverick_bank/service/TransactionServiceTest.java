package com.wipro.maverick_bank.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wipro.maverick_bank.dto.DepositRequestDTO;
import com.wipro.maverick_bank.dto.TransactionResponseDTO;
import com.wipro.maverick_bank.repository.TransactionRepository;

@SpringBootTest
public class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    public void testDeposit() {

        DepositRequestDTO request = new DepositRequestDTO();
        request.setAccountId(101L);
        request.setAmount(5000.0);

        TransactionResponseDTO response = transactionService.deposit(request);

        assertNotNull(response);
        assertNotNull(response.getTransactionId());
        assertEquals(5000.0, response.getAmount());
        assertEquals("DEPOSIT", response.getTransactionType());
        assertEquals("SUCCESS", response.getStatus());
    }
}