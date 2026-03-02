package com.wipro.maverick_bank.service;

import java.util.List;

import com.wipro.maverick_bank.dto.CreateUserRequestDTO;
import com.wipro.maverick_bank.dto.UserDTO;

public interface UserService {

    UserDTO createCustomer(CreateUserRequestDTO request);

    UserDTO createEmployee(CreateUserRequestDTO request);

    UserDTO getUserById(Long userId);

    void deactivateUser(Long userId);

    // ✅ NEW
    List<UserDTO> getAllUsers();
}