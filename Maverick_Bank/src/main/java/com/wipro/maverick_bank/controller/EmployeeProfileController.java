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

import com.wipro.maverick_bank.dto.EmployeeProfileDTO;
import com.wipro.maverick_bank.service.EmployeeProfileService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employee-profile")
public class EmployeeProfileController {

	@Autowired
	private EmployeeProfileService employeeProfileService;

	/**
	 * Create Employee Profile Accessible by: ADMIN
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/add")
	public ResponseEntity<EmployeeProfileDTO> createEmployeeProfile(@Valid @RequestBody EmployeeProfileDTO dto) {

		EmployeeProfileDTO created = employeeProfileService.createEmployeeProfile(dto);

		return ResponseEntity.ok(created);
	}

	/**
	 * Get Employee Profile by ID Accessible by: ADMIN, EMPLOYEE
	 */
	@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
	@GetMapping("/get/{id}")
	public ResponseEntity<EmployeeProfileDTO> getEmployeeProfileById(@PathVariable Long id) {

		EmployeeProfileDTO profile = employeeProfileService.getEmployeeProfileById(id);

		return ResponseEntity.ok(profile);
	}

	/**
	 * Get All Employee Profiles Accessible by: ADMIN
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/get/all")
	public ResponseEntity<List<EmployeeProfileDTO>> getAllEmployeeProfiles() {

		List<EmployeeProfileDTO> profiles = employeeProfileService.getAllEmployeeProfiles();

		return ResponseEntity.ok(profiles);
	}

	/**
	 * Update Employee Profile Accessible by: ADMIN
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<EmployeeProfileDTO> updateEmployeeProfile(@PathVariable Long id,
			@Valid @RequestBody EmployeeProfileDTO dto) {

		EmployeeProfileDTO updated = employeeProfileService.updateEmployeeProfile(id, dto);

		return ResponseEntity.ok(updated);
	}

	/**
	 * Delete Employee Profile Accessible by: ADMIN
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteEmployeeProfile(@PathVariable Long id) {

		employeeProfileService.deleteEmployeeProfile(id);

		return ResponseEntity.ok("Employee profile deleted successfully");
	}
}