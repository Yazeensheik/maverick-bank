package com.wipro.maverick_bank.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wipro.maverick_bank.dto.StatementRequestDTO;
import com.wipro.maverick_bank.dto.StatementResponseDTO;
import com.wipro.maverick_bank.entity.Statement;
import com.wipro.maverick_bank.entity.Transaction;
import com.wipro.maverick_bank.repository.StatementRepository;
import com.wipro.maverick_bank.repository.TransactionRepository;

@Service
public class StatementServiceImpl implements StatementService {

    private TransactionRepository transactionRepository;
    private StatementRepository statementRepository;

    public StatementServiceImpl(TransactionRepository transactionRepository,
                                StatementRepository statementRepository) {
        this.transactionRepository = transactionRepository;
        this.statementRepository = statementRepository;
    }

    @Override
    public StatementResponseDTO generateStatement(StatementRequestDTO request) {

        Long accountId = request.getAccountId();

        LocalDateTime start = request.getStartDate().atStartOfDay();
        LocalDateTime end = request.getEndDate().atTime(23, 59);

        List<Transaction> sent = transactionRepository.findByFromAccountId(accountId);
        List<Transaction> received = transactionRepository.findByToAccountId(accountId);

        double totalDebit = 0;
        double totalCredit = 0;

        for (Transaction t : sent) {
            if (!t.getTransactionDate().isBefore(start) &&
                !t.getTransactionDate().isAfter(end)) {
                totalDebit += t.getAmount();
            }
        }

        for (Transaction t : received) {
            if (!t.getTransactionDate().isBefore(start) &&
                !t.getTransactionDate().isAfter(end)) {
                totalCredit += t.getAmount();
            }
        }

        Statement statement = new Statement();
        statement.setAccountId(accountId);
        statement.setStartDate(request.getStartDate());
        statement.setEndDate(request.getEndDate());
        statement.setTotalCredit(totalCredit);
        statement.setTotalDebit(totalDebit);
        statement.setGeneratedDate(LocalDateTime.now());

        statementRepository.save(statement);

        return mapToDTO(statement);
    }

    @Override
    public List<StatementResponseDTO> getStatements(Long accountId) {

        List<Statement> list = statementRepository.findByAccountId(accountId);
        List<StatementResponseDTO> responseList = new ArrayList<>();

        for (Statement s : list) {
            responseList.add(mapToDTO(s));
        }

        return responseList;
    }

    private StatementResponseDTO mapToDTO(Statement s) {

        StatementResponseDTO dto = new StatementResponseDTO();
        dto.setStatementId(s.getStatementId());
        dto.setAccountId(s.getAccountId());
        dto.setStartDate(s.getStartDate());
        dto.setEndDate(s.getEndDate());
        dto.setTotalCredit(s.getTotalCredit());
        dto.setTotalDebit(s.getTotalDebit());
        dto.setGeneratedDate(s.getGeneratedDate());

        return dto;
    }
}
