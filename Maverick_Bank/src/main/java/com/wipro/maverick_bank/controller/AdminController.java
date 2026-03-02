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

import com.wipro.maverick_bank.dto.AdminCreateUserDTO;
import com.wipro.maverick_bank.dto.LoanDTO;
import com.wipro.maverick_bank.dto.UserDTO;
import com.wipro.maverick_bank.service.LoanService;
import com.wipro.maverick_bank.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	private final UserService userService;
	private final LoanService loanService;
	
	public AdminController(UserService userService, LoanService loanService) {
		this.userService=userService;
		this.loanService=loanService;
	}
	
	@PostMapping("/users")
	public ResponseEntity<UserDTO> createUser(
			@Valid @RequestBody AdminCreateUserDTO dto){
		UserDTO createdUser=userService.createUser(dto);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable Long id ){
		return ResponseEntity.ok(userService.getUserById(id));
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<UserDTO>> getAllUsers(){
		return ResponseEntity.ok(userService.getAllUsers());
	}
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<Void> deactivateUser(@PathVariable Long id){
		userService.deactivateUser(id);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/loans")
	public ResponseEntity<LoanDTO> createLoan(@Valid @RequestBody LoanDTO loanDTO){
		LoanDTO createdLoan=loanService.createLoan(loanDTO);
		return new ResponseEntity<>(createdLoan, HttpStatus.CREATED);
	}
	
	@GetMapping("/loans")
	public ResponseEntity<List<LoanDTO>> getAllLoans(){
		return ResponseEntity.ok(loanService.getAllLoans());
	}
	
	@PutMapping("/loans/{id}")
	public ResponseEntity<LoanDTO> updateLoan(@PathVariable Long id, @Valid @RequestBody LoanDTO loanDTO){
		return ResponseEntity.ok(loanService.updateLoan(id, loanDTO));
	}
	
	@GetMapping("/loans/{id}")
	public ResponseEntity<LoanDTO> getLoanById(@PathVariable Long id){
		return ResponseEntity.ok(loanService.getLoanById(id));
	}
	
	@DeleteMapping("/loans/{id}")
	public ResponseEntity<Void> deleteLoan(@PathVariable Long id){
		loanService.deleteLoan(id);
		return ResponseEntity.noContent().build();
	}
}
