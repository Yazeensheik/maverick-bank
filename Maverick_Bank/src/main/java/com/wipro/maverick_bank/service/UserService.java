package com.wipro.maverick_bank.service;

import java.util.List;

import com.wipro.maverick_bank.dto.CreateUserRequestDTO;
import com.wipro.maverick_bank.dto.UserDTO;

public interface UserService {

    // Create new user (Customer / Employee / Admin)
    UserDTO createUser(CreateUserRequestDTO request, String roleName);

    // Fetch single user by ID
    UserDTO getUserById(Long userId);

    // Deactivate user (Soft delete)
    void deactivateUser(Long userId);

    // Get all users
    List<UserDTO> getAllUsers();
}