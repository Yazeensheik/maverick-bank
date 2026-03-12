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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/employee-profile")
@RequiredArgsConstructor
public class EmployeeProfileController {

	@Autowired
    private  EmployeeProfileService employeeProfileService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<EmployeeProfileDTO> createEmployeeProfile(@RequestBody EmployeeProfileDTO dto) {
        EmployeeProfileDTO createdProfile = employeeProfileService.createEmployeeProfile(dto);
        return ResponseEntity.ok(createdProfile);
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @GetMapping("/get/all")
    public ResponseEntity<List<EmployeeProfileDTO>> getAllEmployeeProfiles() {
        List<EmployeeProfileDTO> profiles = employeeProfileService.getAllEmployeeProfiles();
        return ResponseEntity.ok(profiles);
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @GetMapping("/get/{id}")
    public ResponseEntity<EmployeeProfileDTO> getEmployeeProfileById(@PathVariable Long id) {
        EmployeeProfileDTO profile = employeeProfileService.getEmployeeProfileById(id);
        return ResponseEntity.ok(profile);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeProfileDTO> updateEmployeeProfile(@PathVariable Long id,
                                                                    @RequestBody EmployeeProfileDTO dto) {
        EmployeeProfileDTO updatedProfile = employeeProfileService.updateEmployeeProfile(id, dto);
        return ResponseEntity.ok(updatedProfile);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployeeProfile(@PathVariable Long id) {
        employeeProfileService.deleteEmployeeProfile(id);
        return ResponseEntity.ok("Employee profile deleted successfully");
    }
}