package com.wipro.maverick_bank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.maverick_bank.dto.CreateUserRequestDTO;
import com.wipro.maverick_bank.dto.UserDTO;
import com.wipro.maverick_bank.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * Create Customer Accessible by: ADMIN
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/customer")
	public ResponseEntity<UserDTO> createCustomer(@Valid @RequestBody CreateUserRequestDTO request) {

		UserDTO user = userService.createUser(request, "CUSTOMER");
		return ResponseEntity.ok(user);
	}

	/**
	 * Create Bank Employee Accessible by: ADMIN
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/employee")
	public ResponseEntity<UserDTO> createEmployee(@Valid @RequestBody CreateUserRequestDTO request) {

		UserDTO user = userService.createUser(request, "EMPLOYEE");
		return ResponseEntity.ok(user);
	}

	/**
	 * Get User by ID Accessible by: ADMIN, EMPLOYEE
	 */
	@PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {

		UserDTO user = userService.getUserById(id);
		return ResponseEntity.ok(user);
	}

	/**
	 * Deactivate User (Soft Delete) Accessible by: ADMIN
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/deactivate")
	public ResponseEntity<String> deactivateUser(@PathVariable Long id) {

		userService.deactivateUser(id);
		return ResponseEntity.ok("User deactivated successfully");
	}

	/**
	 * Get All Users Accessible by: ADMIN
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<UserDTO>> getAllUsers() {

		List<UserDTO> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}
}