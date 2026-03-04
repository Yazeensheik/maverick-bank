package com.wipro.maverick_bank.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.maverick_bank.dto.TransactionDTO;
import com.wipro.maverick_bank.service.TransactionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/deposit")
    public TransactionDTO deposit(@Valid @RequestBody TransactionDTO dto) {
        return transactionService.deposit(dto);
    }

    @PostMapping("/withdraw")
    public TransactionDTO withdraw(@Valid @RequestBody TransactionDTO dto) {
        return transactionService.withdraw(dto);
    }

    @PostMapping("/transfer")
    public TransactionDTO transfer(@Valid @RequestBody TransactionDTO dto) {
        return transactionService.transfer(dto);
    }

    @GetMapping("/{accountId}")
    public List<TransactionDTO> getTransactions(@PathVariable Long accountId) {
        return transactionService.getTransactionsByAccount(accountId);
    }
}