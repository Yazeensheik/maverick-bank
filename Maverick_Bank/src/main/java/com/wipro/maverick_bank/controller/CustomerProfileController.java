package com.wipro.maverick_bank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.wipro.maverick_bank.dto.CustomerProfileDTO;
import com.wipro.maverick_bank.service.CustomerProfileService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customer-profile")
public class CustomerProfileController {

	@Autowired
	private CustomerProfileService customerProfileService;

	/**
	 * Create Customer Profile Accessible by: ADMIN
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/add")
	public ResponseEntity<CustomerProfileDTO> createCustomerProfile(@Valid @RequestBody CustomerProfileDTO dto) {

		CustomerProfileDTO created = customerProfileService.createCustomerProfile(dto);

		return ResponseEntity.ok(created);
	}

	/**
	 * Get Customer Profile by ID Accessible by: ADMIN, EMPLOYEE
	 */
	@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
	@GetMapping("/get/{id}")
	public ResponseEntity<CustomerProfileDTO> getCustomerProfileById(@PathVariable Long id) {

		CustomerProfileDTO profile = customerProfileService.getCustomerProfileById(id);

		return ResponseEntity.ok(profile);
	}

	/**
	 * Get All Customer Profiles Accessible by: ADMIN
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/get/all")
	public ResponseEntity<List<CustomerProfileDTO>> getAllCustomerProfiles() {

		List<CustomerProfileDTO> profiles = customerProfileService.getAllCustomerProfiles();

		return ResponseEntity.ok(profiles);
	}

	/**
	 * Update Customer Profile Accessible by: ADMIN
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<CustomerProfileDTO> updateCustomerProfile(@PathVariable Long id,
			@Valid @RequestBody CustomerProfileDTO dto) {

		CustomerProfileDTO updated = customerProfileService.updateCustomerProfile(id, dto);

		return ResponseEntity.ok(updated);
	}

	/**
	 * Delete Customer Profile Accessible by: ADMIN
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteCustomerProfile(@PathVariable Long id) {

		customerProfileService.deleteCustomerProfile(id);

		return ResponseEntity.ok("Customer profile deleted successfully");
	}
}