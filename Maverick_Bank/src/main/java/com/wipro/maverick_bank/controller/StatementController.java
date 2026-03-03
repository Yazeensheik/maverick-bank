package com.wipro.maverick_bank.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.maverick_bank.dto.StatementRequestDTO;
import com.wipro.maverick_bank.dto.StatementResponseDTO;
import com.wipro.maverick_bank.service.StatementService;

@RestController
@RequestMapping("/api/statements")
public class StatementController {

    private StatementService statementService;

    public StatementController(StatementService statementService) {
        this.statementService = statementService;
    }

    @PostMapping("/generate")
    public StatementResponseDTO generateStatement(@RequestBody StatementRequestDTO request) {
        return statementService.generateStatement(request);
    }

    @GetMapping("/getby/{accountId}")
    public List<StatementResponseDTO> getStatements(@PathVariable Long accountId) {
        return statementService.getStatements(accountId);
    }
}