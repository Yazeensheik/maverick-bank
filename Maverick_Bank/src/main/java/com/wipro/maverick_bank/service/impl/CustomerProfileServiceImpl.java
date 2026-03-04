package com.wipro.maverick_bank.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.maverick_bank.dto.CustomerProfileDTO;
import com.wipro.maverick_bank.entity.CustomerProfile;
import com.wipro.maverick_bank.entity.User;
import com.wipro.maverick_bank.repository.CustomerProfileRepository;
import com.wipro.maverick_bank.repository.UserRepository;
import com.wipro.maverick_bank.service.CustomerProfileService;

@Service
public class CustomerProfileServiceImpl implements CustomerProfileService {

    @Autowired
    private CustomerProfileRepository customerProfileRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public CustomerProfileDTO createCustomerProfile(CustomerProfileDTO dto) {

        CustomerProfile profile = new CustomerProfile();

        profile.setFullName(dto.getFullName());
        profile.setEmail(dto.getEmail());
        profile.setPhone(dto.getPhone());

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        profile.setUser(user);

        CustomerProfile saved = customerProfileRepository.save(profile);

        return mapToDTO(saved);
    }

    @Override
    public CustomerProfileDTO getCustomerProfileById(Long id) {

        CustomerProfile profile = customerProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer profile not found"));

        return mapToDTO(profile);
    }

    @Override
    public List<CustomerProfileDTO> getAllCustomerProfiles() {

        return customerProfileRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerProfileDTO updateCustomerProfile(Long id, CustomerProfileDTO dto) {

        CustomerProfile profile = customerProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer profile not found"));

        profile.setFullName(dto.getFullName());
        profile.setEmail(dto.getEmail());
        profile.setPhone(dto.getPhone());

        CustomerProfile updated = customerProfileRepository.save(profile);

        return mapToDTO(updated);
    }

    @Override
    public void deleteCustomerProfile(Long id) {

        CustomerProfile profile = customerProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer profile not found"));

        customerProfileRepository.delete(profile);
    }

    private CustomerProfileDTO mapToDTO(CustomerProfile profile) {

        CustomerProfileDTO dto = new CustomerProfileDTO();

        dto.setId(profile.getId());
        dto.setFullName(profile.getFullName());
        dto.setEmail(profile.getEmail());
        dto.setPhone(profile.getPhone());

        if(profile.getUser() != null) {
            dto.setUserId(profile.getUser().getId());
        }

        return dto;
    }
}