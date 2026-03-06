package com.wipro.maverick_bank.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wipro.maverick_bank.dto.TransactionDTO;

@SpringBootTest
public class StatementServiceTest {

    @Autowired
    private StatementService statementService;

    @Test
    void testGetLast10Transactions() {


    }
}