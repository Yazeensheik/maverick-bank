package com.wipro.maverick_bank.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.maverick_bank.entity.CustomerLoan;
import com.wipro.maverick_bank.service.CustomerLoanService;

@RestController
@RequestMapping("/api/customer-loans")
public class CustomerLoanController {
	
	private final CustomerLoanService customerLoanService;
	
	public CustomerLoanController(CustomerLoanService customerLoanService) {
		this.customerLoanService=customerLoanService;
	}
	
	
	@PostMapping("/application/{applicationId}")
    public ResponseEntity<CustomerLoan> createCustomerLoan(
            @PathVariable Long applicationId) {

        CustomerLoan loan =
                customerLoanService.createCustomerLoan(applicationId);

        return ResponseEntity.ok(loan);
    }
	
	@GetMapping("/{customerLoanId}")
    public ResponseEntity<CustomerLoan> getCustomerLoanById(
            @PathVariable Long customerLoanId) {

        return ResponseEntity.ok(
                customerLoanService.getCustomerLoanById(customerLoanId));
    }

    @GetMapping
    public ResponseEntity<List<CustomerLoan>> getAllCustomerLoans() {
        return ResponseEntity.ok(
                customerLoanService.getAllCustomerLoans());
    }
	
	@PutMapping("/{customerLoanId}/outstanding")
    public ResponseEntity<CustomerLoan> updateOutstandingAmount(
            @PathVariable Long customerLoanId,
            @RequestParam double amount) {

        return ResponseEntity.ok(
                customerLoanService.updateOutstandingAmount(customerLoanId, amount));
    }
	
	@PutMapping("/{customerLoanId}/close")
    public ResponseEntity<CustomerLoan> closeLoan(
            @PathVariable Long customerLoanId) {

        return ResponseEntity.ok(
                customerLoanService.closeLoan(customerLoanId));
    }
}
