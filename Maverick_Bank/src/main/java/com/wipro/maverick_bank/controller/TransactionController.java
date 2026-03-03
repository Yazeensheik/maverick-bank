package com.wipro.maverick_bank.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.maverick_bank.dto.DepositRequestDTO;
import com.wipro.maverick_bank.dto.TransactionResponseDTO;
import com.wipro.maverick_bank.dto.TransferRequestDTO;
import com.wipro.maverick_bank.dto.WithdrawRequestDTO;
import com.wipro.maverick_bank.service.TransactionService;

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