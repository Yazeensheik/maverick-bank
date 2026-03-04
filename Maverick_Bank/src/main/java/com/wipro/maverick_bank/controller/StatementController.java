package com.wipro.maverick_bank.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.maverick_bank.dto.StatementDTO;
import com.wipro.maverick_bank.dto.TransactionDTO;
import com.wipro.maverick_bank.service.StatementService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/statements")
@RequiredArgsConstructor
public class StatementController {

    private final StatementService statementService;

    @PostMapping("/generate")
    public List<TransactionDTO> generateStatement(@Valid @RequestBody StatementDTO dto) {
        return statementService.generateStatement(dto);
    }
}