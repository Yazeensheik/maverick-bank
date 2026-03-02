package com.wipro.maverick_bank.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wipro.maverick_bank.dto.CreateUserRequestDTO;
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
        this.userService = userService;
        this.loanService = loanService;
    }

    /* =========================
       USER MANAGEMENT (ADMIN)
       ========================= */

    /**
     * Create Customer
     * ROLE_ADMIN
     */
    @PostMapping("/users/customer")
    public ResponseEntity<UserDTO> createCustomer(
            @Valid @RequestBody CreateUserRequestDTO request) {

        UserDTO createdUser = userService.createCustomer(request);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    /**
     * Create Employee
     * ROLE_ADMIN
     */
    @PostMapping("/users/employee")
    public ResponseEntity<UserDTO> createEmployee(
            @Valid @RequestBody CreateUserRequestDTO request) {

        UserDTO createdUser = userService.createEmployee(request);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    /**
     * Get User by ID
     * ROLE_ADMIN
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {

        return ResponseEntity.ok(userService.getUserById(id));
    }

    /**
     * Get All Users
     * ROLE_ADMIN
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {

        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * Deactivate User (Soft delete)
     * ROLE_ADMIN
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deactivateUser(@PathVariable Long id) {

        userService.deactivateUser(id);
        return ResponseEntity.noContent().build();
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