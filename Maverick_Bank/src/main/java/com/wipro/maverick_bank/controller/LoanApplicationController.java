package com.wipro.maverick_bank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.maverick_bank.dto.LoanApplicationDTO;
import com.wipro.maverick_bank.entity.Loan;
import com.wipro.maverick_bank.repository.LoanRepository;
import com.wipro.maverick_bank.service.LoanApplicationService;

@RestController
@RequestMapping("/api/loan-applications")
public class LoanApplicationController {
	
	@Autowired
	private LoanRepository loanRepository;

    @Autowired
    private LoanApplicationService loanApplicationService;

    @GetMapping("/loans")
    public ResponseEntity<List<Loan>> getAvailableLoans(){

        List<Loan> loans = loanRepository.findAll();

        return ResponseEntity.ok(loans);
    }
    
    @PostMapping("/apply")
    public ResponseEntity<LoanApplicationDTO> applyLoan(@RequestBody LoanApplicationDTO dto) {
        return ResponseEntity.ok(loanApplicationService.applyLoan(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanApplicationDTO> getLoanApplication(@PathVariable Long id) {
        return ResponseEntity.ok(loanApplicationService.getApplicationById(id));
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<LoanApplicationDTO>> getAllApplications() {
        return ResponseEntity.ok(loanApplicationService.getAllApplications());
    }
    
    @PutMapping("/{id}/approve")
    public ResponseEntity<String> approveLoan(@PathVariable Long id) {

        loanApplicationService.approveLoan(id);

        return ResponseEntity.ok("Loan Approved");
    }
    
    @PutMapping("/{id}/reject")
    public ResponseEntity<String> rejectLoan(@PathVariable Long id) {

        loanApplicationService.rejectLoan(id);

        return ResponseEntity.ok("Loan Rejected");
    }
}