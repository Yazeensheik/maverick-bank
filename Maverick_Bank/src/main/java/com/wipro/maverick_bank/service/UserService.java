package com.wipro.maverick_bank.service;

import com.wipro.maverick_bank.dto.CreateUserRequestDTO;
import com.wipro.maverick_bank.dto.UserDTO;

public interface UserService {

    // Create a new customer user
    UserDTO createCustomer(CreateUserRequestDTO request);

    // Create a new bank employee user
    UserDTO createEmployee(CreateUserRequestDTO request);

    // Fetch user details by user id
    UserDTO getUserById(Long userId);

    // Deactivate a user (soft delete)
    void deactivateUser(Long userId);
}