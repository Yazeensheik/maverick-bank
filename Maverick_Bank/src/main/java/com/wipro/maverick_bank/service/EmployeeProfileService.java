package com.wipro.maverick_bank.service;

import java.util.List;

import com.wipro.maverick_bank.dto.EmployeeProfileDTO;

public interface EmployeeProfileService {

    EmployeeProfileDTO createEmployeeProfile(EmployeeProfileDTO dto);

    EmployeeProfileDTO getEmployeeProfileById(Long id);

    List<EmployeeProfileDTO> getAllEmployeeProfiles();

    EmployeeProfileDTO updateEmployeeProfile(Long id, EmployeeProfileDTO dto);

    void deleteEmployeeProfile(Long id);
}