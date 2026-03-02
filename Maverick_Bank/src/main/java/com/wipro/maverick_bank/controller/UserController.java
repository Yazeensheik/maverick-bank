package com.wipro.maverick_bank.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wipro.maverick_bank.dto.CreateUserRequestDTO;
import com.wipro.maverick_bank.dto.UserDTO;
import com.wipro.maverick_bank.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Create Customer
     * Accessible by: ADMIN
     */
    @PostMapping("/customer")
    public ResponseEntity<UserDTO> createCustomer(
            @Valid @RequestBody CreateUserRequestDTO request) {

        UserDTO user = userService.createCustomer(request);
        return ResponseEntity.ok(user);
    }

    /**
     * Create Bank Employee
     * Accessible by: ADMIN
     */
    @PostMapping("/employee")
    public ResponseEntity<UserDTO> createEmployee(
            @Valid @RequestBody CreateUserRequestDTO request) {

        UserDTO user = userService.createEmployee(request);
        return ResponseEntity.ok(user);
    }

    /**
     * Get User Details
     * Accessible by: ADMIN, EMPLOYEE
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {

        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Deactivate User 
     * Accessible by: ADMIN
     */
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivateUser(@PathVariable Long id) {

        userService.deactivateUser(id);
        return ResponseEntity.ok("User deactivated successfully");
    }
}