package com.wipro.maverick_bank.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.maverick_bank.dto.LoanApplicationDTO;
import com.wipro.maverick_bank.entity.Loan;
import com.wipro.maverick_bank.entity.LoanApplication;
import com.wipro.maverick_bank.repository.LoanApplicationRepository;
import com.wipro.maverick_bank.repository.LoanRepository;
import com.wipro.maverick_bank.service.LoanApplicationService;

@Service
public class LoanApplicationServiceImpl implements LoanApplicationService {

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Override
    public LoanApplicationDTO applyLoan(LoanApplicationDTO dto) {

        Loan loan = loanRepository.findById(dto.getLoanId())
                .orElseThrow(() -> new RuntimeException("Loan type not found"));

        LoanApplication application = new LoanApplication();

        application.setLoan(loan);
        application.setAmount(dto.getAmount());
        application.setPurpose(dto.getPurpose());
        application.setStatus("PENDING");

        LoanApplication savedApplication = loanApplicationRepository.save(application);

        return new LoanApplicationDTO(
                savedApplication.getId(),
                savedApplication.getLoan().getId(),
                savedApplication.getAmount(),
                savedApplication.getPurpose(),
                savedApplication.getStatus()
        );
    }
    @Override
    public LoanApplicationDTO getApplicationById(Long id) {

        LoanApplication application = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        return new LoanApplicationDTO(
                application.getId(),
                application.getLoan().getId(),
                application.getAmount(),
                application.getPurpose(),
                application.getStatus()
        );
    }
    
    @Override
    public List<LoanApplicationDTO> getAllApplications() {

        List<LoanApplication> applications = loanApplicationRepository.findAll();

        List<LoanApplicationDTO> dtoList = new ArrayList<>();

        for(LoanApplication app : applications) {

        	dtoList.add(new LoanApplicationDTO(
        	        app.getId(),
        	        app.getLoan().getId(),
        	        app.getAmount(),
        	        app.getPurpose(),
        	        app.getStatus()
        	));
        }

        return dtoList;
    }
    
    @Override
    public void approveLoan(Long id) {

        LoanApplication application = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        application.setStatus("APPROVED");

        loanApplicationRepository.save(application);
    }
    
    @Override
    public void rejectLoan(Long id) {

        LoanApplication application = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        application.setStatus("REJECTED");

        loanApplicationRepository.save(application);
    }
}