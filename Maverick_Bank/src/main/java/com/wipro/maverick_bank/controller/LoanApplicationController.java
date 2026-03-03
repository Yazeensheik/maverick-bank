package com.wipro.maverick_bank.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.maverick_bank.dto.LoanApplicationDTO;
import com.wipro.maverick_bank.dto.LoanApprovalDTO;
import com.wipro.maverick_bank.service.LoanApplicationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/loan-applications")
public class LoanApplicationController {

	private final LoanApplicationService loanApplicationService;

	public LoanApplicationController(LoanApplicationService loanApplicationService) {
		this.loanApplicationService = loanApplicationService;
	}

	@PostMapping("/user/{userId}")
	public ResponseEntity<LoanApplicationDTO> applyforLoan(@PathVariable Long userId,
			@Valid @RequestBody LoanApplicationDTO dto) {
		LoanApplicationDTO response = loanApplicationService.applyForLoan(userId, dto);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<LoanApplicationDTO> getApplicationByID(@PathVariable Long id) {
		return ResponseEntity.ok(loanApplicationService.getApplicationById(id));
	}

	@GetMapping
	public ResponseEntity<List<LoanApplicationDTO>> getAllApplications() {
		return ResponseEntity.ok(loanApplicationService.getAllApplications());
	}

	@PutMapping("/{id}/decision")
	public ResponseEntity<LoanApprovalDTO> approveOrRejectLoan(@PathVariable Long id,
			@Valid @RequestBody LoanApprovalDTO approvalDTO) {
		LoanApprovalDTO response = loanApplicationService.approveOrRejectLoan(id, approvalDTO);
		return ResponseEntity.ok(response);
	}
}
