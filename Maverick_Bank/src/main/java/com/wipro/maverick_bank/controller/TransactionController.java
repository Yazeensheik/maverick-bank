package com.wipro.maverick_bank.controller;

import com.wipro.maverick_bank.dto.*;
import com.wipro.maverick_bank.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/deposit")
    public TransactionResponseDTO deposit(@RequestBody DepositRequestDTO request) {
        return transactionService.deposit(request);
    }

    @PostMapping("/withdraw")
    public TransactionResponseDTO withdraw(@RequestBody WithdrawRequestDTO request) {
        return transactionService.withdraw(request);
    }

    @PostMapping("/transfer")
    public TransactionResponseDTO transfer(@RequestBody TransferRequestDTO request) {
        return transactionService.transfer(request);
    }

    @GetMapping("/getall/{accountId}")
    public List<TransactionResponseDTO> getAllTransactions(@PathVariable Long accountId) {
        return transactionService.getAllTransactions(accountId);
    }

    @GetMapping("/getby/{accountId}/last10")
    public List<TransactionResponseDTO> getLast10Transactions(@PathVariable Long accountId) {
        return transactionService.getLast10Transactions(accountId);
    }
}