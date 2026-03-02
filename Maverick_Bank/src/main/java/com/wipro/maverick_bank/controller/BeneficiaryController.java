package com.wipro.maverick_bank.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.maverick_bank.dto.BeneficiaryDTO;
import com.wipro.maverick_bank.service.BeneficiaryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/beneficiaries")
@RequiredArgsConstructor
public class BeneficiaryController {

	private final BeneficiaryService beneficiaryService;

	// Add Beneficiary (Customer Use Case)
	@PostMapping
	public ResponseEntity<BeneficiaryDTO> addBeneficiary(@RequestBody BeneficiaryDTO beneficiaryDTO) {
		BeneficiaryDTO createdBeneficiary = beneficiaryService.addBeneficiary(beneficiaryDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdBeneficiary);
	}

	// View Beneficiary Details
	@GetMapping("/{id}")
	public ResponseEntity<BeneficiaryDTO> getBeneficiaryById(@PathVariable Long id) {
		return ResponseEntity.ok(beneficiaryService.getBeneficiaryById(id));
	}

	// View All Beneficiaries
	@GetMapping
	public ResponseEntity<List<BeneficiaryDTO>> getAllBeneficiaries() {
		return ResponseEntity.ok(beneficiaryService.getAllBeneficiaries());
	}

	// Delete Beneficiary
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteBeneficiary(@PathVariable Long id) {
		beneficiaryService.deleteBeneficiary(id);
		return ResponseEntity.ok("Beneficiary deleted successfully");
	}
}