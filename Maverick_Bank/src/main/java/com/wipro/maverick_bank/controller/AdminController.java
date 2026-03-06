package com.wipro.maverick_bank.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.maverick_bank.dto.CreateUserRequestDTO;
import com.wipro.maverick_bank.dto.LoanDTO;
import com.wipro.maverick_bank.dto.UserDTO;
import com.wipro.maverick_bank.service.LoanService;
import com.wipro.maverick_bank.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')") // ADMIN ONLY
public class AdminController {

    private final UserService userService;
    private final LoanService loanService;

    public AdminController(UserService userService, LoanService loanService) {
        this.userService = userService;
        this.loanService = loanService;
    }

    /* =========================
       USER MANAGEMENT (ADMIN)
       ========================= */

    @PostMapping("/users/customer")
    public ResponseEntity<UserDTO> createCustomer(
            @Valid @RequestBody CreateUserRequestDTO request) {

        UserDTO createdUser =
                userService.createUser(request, "CUSTOMER");

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("/users/employee")
    public ResponseEntity<UserDTO> createEmployee(
            @Valid @RequestBody CreateUserRequestDTO request) {

        UserDTO createdUser =
                userService.createUser(request, "EMPLOYEE");

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {

        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/users/{id}/deactivate")
    public ResponseEntity<String> deactivateUser(@PathVariable Long id) {

        userService.deactivateUser(id);
        return ResponseEntity.ok("User deactivated successfully");
    }

    /* =========================
       LOAN MANAGEMENT (ADMIN)
       ========================= */

    @PostMapping("/loans")
    public ResponseEntity<LoanDTO> createLoan(
            @Valid @RequestBody LoanDTO loanDTO) {

        LoanDTO createdLoan = loanService.createLoan(loanDTO);
        return new ResponseEntity<>(createdLoan, HttpStatus.CREATED);
    }

    @GetMapping("/loans")
    public ResponseEntity<List<LoanDTO>> getAllLoans() {

        return ResponseEntity.ok(loanService.getAllLoans());
    }

    @GetMapping("/loans/{id}")
    public ResponseEntity<LoanDTO> getLoanById(@PathVariable Long id) {

        return ResponseEntity.ok(loanService.getLoanById(id));
    }

    @PutMapping("/loans/{id}")
    public ResponseEntity<LoanDTO> updateLoan(
            @PathVariable Long id,
            @Valid @RequestBody LoanDTO loanDTO) {

        return ResponseEntity.ok(loanService.updateLoan(id, loanDTO));
    }

    @DeleteMapping("/loans/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {

        loanService.deleteLoan(id);
        return ResponseEntity.noContent().build();
    }
}