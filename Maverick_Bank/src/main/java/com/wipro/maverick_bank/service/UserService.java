package com.wipro.maverick_bank.service;

import java.util.List;

import com.wipro.maverick_bank.dto.CreateUserRequestDTO;
import com.wipro.maverick_bank.dto.UserDTO;

public interface UserService {


    /**
     * Create a user with a specific role
     * Example roles: ROLE_CUSTOMER, ROLE_EMPLOYEE
     */
    UserDTO createUser(CreateUserRequestDTO request, String roleName);

    /**
     * Get user details by user ID
     */
    UserDTO getUserById(Long userId);

    /**
     * Deactivate (soft delete) a user
     */
    void deactivateUser(Long userId);

    /**
     * Get all users
     */

    List<UserDTO> getAllUsers();
}