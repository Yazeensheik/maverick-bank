package com.wipro.maverick_bank.service;

import java.util.List;

import com.wipro.maverick_bank.dto.CustomerProfileDTO;

public interface CustomerProfileService {

    CustomerProfileDTO createCustomerProfile(CustomerProfileDTO dto);

    CustomerProfileDTO getCustomerProfileById(Long id);

    List<CustomerProfileDTO> getAllCustomerProfiles();

    CustomerProfileDTO updateCustomerProfile(Long id, CustomerProfileDTO dto);

    void deleteCustomerProfile(Long id);
}