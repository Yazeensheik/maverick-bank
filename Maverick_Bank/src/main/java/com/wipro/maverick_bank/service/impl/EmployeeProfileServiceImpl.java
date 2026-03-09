package com.wipro.maverick_bank.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wipro.maverick_bank.dto.EmployeeProfileDTO;
import com.wipro.maverick_bank.entity.EmployeeProfile;
import com.wipro.maverick_bank.entity.User;
import com.wipro.maverick_bank.exception.ResourceNotFoundException;
import com.wipro.maverick_bank.repository.EmployeeProfileRepository;
import com.wipro.maverick_bank.repository.UserRepository;
import com.wipro.maverick_bank.service.EmployeeProfileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeProfileServiceImpl implements EmployeeProfileService {

    private final EmployeeProfileRepository employeeProfileRepository;
    private final UserRepository userRepository;

    @Override
    public EmployeeProfileDTO createEmployeeProfile(EmployeeProfileDTO dto) {

        if (employeeProfileRepository.findByUserId(dto.getUserId()).isPresent()) {
            throw new RuntimeException("Employee profile already exists for this user");
        }

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + dto.getUserId()));

        EmployeeProfile profile = new EmployeeProfile();
        profile.setFullName(dto.getFullName());
        profile.setEmail(dto.getEmail());
        profile.setPhone(dto.getPhone());
        profile.setDepartment(dto.getDepartment());
        profile.setUser(user);

        EmployeeProfile savedProfile = employeeProfileRepository.save(profile);
        return mapToDTO(savedProfile);
    }

    @Override
    public List<EmployeeProfileDTO> getAllEmployeeProfiles() {
        return employeeProfileRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeProfileDTO getEmployeeProfileById(Long id) {
        EmployeeProfile profile = employeeProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee profile not found with id: " + id));
        return mapToDTO(profile);
    }

    @Override
    public EmployeeProfileDTO updateEmployeeProfile(Long id, EmployeeProfileDTO dto) {
        EmployeeProfile existingProfile = employeeProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee profile not found with id: " + id));

        existingProfile.setFullName(dto.getFullName());
        existingProfile.setEmail(dto.getEmail());
        existingProfile.setPhone(dto.getPhone());
        existingProfile.setDepartment(dto.getDepartment());

        if (dto.getUserId() != null && !dto.getUserId().equals(existingProfile.getUser().getId())) {
            if (employeeProfileRepository.findByUserId(dto.getUserId()).isPresent()) {
                throw new RuntimeException("Employee profile already exists for this user");
            }

            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + dto.getUserId()));
            existingProfile.setUser(user);
        }

        EmployeeProfile updatedProfile = employeeProfileRepository.save(existingProfile);
        return mapToDTO(updatedProfile);
    }

    @Override
    public void deleteEmployeeProfile(Long id) {
        EmployeeProfile profile = employeeProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee profile not found with id: " + id));
        employeeProfileRepository.delete(profile);
    }

    private EmployeeProfileDTO mapToDTO(EmployeeProfile profile) {
        EmployeeProfileDTO dto = new EmployeeProfileDTO();
        dto.setId(profile.getId());
        dto.setFullName(profile.getFullName());
        dto.setEmail(profile.getEmail());
        dto.setPhone(profile.getPhone());
        dto.setDepartment(profile.getDepartment());
        dto.setUserId(profile.getUser() != null ? profile.getUser().getId() : null);
        return dto;
    }
}