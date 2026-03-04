package com.wipro.maverick_bank.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.maverick_bank.dto.TransactionDTO;
import com.wipro.maverick_bank.service.StatementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/statements")
@RequiredArgsConstructor
public class StatementController {

    private final StatementService statementService;

    @GetMapping("/last10/{accountId}")
    public ResponseEntity<List<TransactionDTO>> getLast10Transactions(@PathVariable Long accountId) {
        return ResponseEntity.ok(statementService.getLast10Transactions(accountId));
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<TransactionDTO>> getTransactionsBetweenDates(
            @RequestParam Long accountId,
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {

        return ResponseEntity.ok(
                statementService.getTransactionsBetweenDates(accountId, startDate, endDate));
    }
}