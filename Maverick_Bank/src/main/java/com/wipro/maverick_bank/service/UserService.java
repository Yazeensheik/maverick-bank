package com.wipro.maverick_bank.service;

import com.wipro.maverick_bank.dto.UserDTO;

public interface UserService {

    UserDTO createCustomer(UserDTO userDTO);

    UserDTO createEmployee(UserDTO userDTO);

    UserDTO getUserById(Long userId);

    void deactivateUser(Long userId);
}