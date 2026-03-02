package com.wipro.maverick_bank.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.maverick_bank.dto.AccountDTO;
import com.wipro.maverick_bank.service.AccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

	private final AccountService accountService;

	// Open Account (Customer Use Case)
	@PostMapping
	public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO) {
		AccountDTO createdAccount = accountService.createAccount(accountDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
	}

	// View Account Details
	@GetMapping("/{id}")
	public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long id) {
		return ResponseEntity.ok(accountService.getAccountById(id));
	}

	// View All Accounts (Employee/Admin Use Case)
	@GetMapping
	public ResponseEntity<List<AccountDTO>> getAllAccounts() {
		return ResponseEntity.ok(accountService.getAllAccounts());
	}

	// Update Account (Status / Balance / Type)
	@PutMapping("/{id}")
	public ResponseEntity<AccountDTO> updateAccount(@PathVariable Long id, @RequestBody AccountDTO accountDTO) {
		return ResponseEntity.ok(accountService.updateAccount(id, accountDTO));
	}

	// Close Account (Delete Request)
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
		accountService.deleteAccount(id);
		return ResponseEntity.ok("Account closed successfully");
	}
}