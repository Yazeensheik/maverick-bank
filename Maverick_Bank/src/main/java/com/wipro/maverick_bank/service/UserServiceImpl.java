package com.wipro.maverick_bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wipro.maverick_bank.dto.CreateUserRequestDTO;
import com.wipro.maverick_bank.dto.UserDTO;
import com.wipro.maverick_bank.entity.Role;
import com.wipro.maverick_bank.entity.User;
import com.wipro.maverick_bank.exception.ResourceNotFoundException;
import com.wipro.maverick_bank.repository.RoleRepository;
import com.wipro.maverick_bank.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO createCustomer(CreateUserRequestDTO request) {

        Role role = roleRepository.findByName("ROLE_CUSTOMER")
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role ROLE_CUSTOMER not found"));

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);
        user.setActive(true);

        user = userRepository.save(user);

        return mapToDTO(user);
    }

    @Override
    public UserDTO createEmployee(CreateUserRequestDTO request) {

        Role role = roleRepository.findByName("ROLE_EMPLOYEE")
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role ROLE_EMPLOYEE not found"));

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);
        user.setActive(true);

        user = userRepository.save(user);

        return mapToDTO(user);
    }

    @Override
    public UserDTO getUserById(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return mapToDTO(user);
    }

    @Override
    public void deactivateUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        user.setActive(false);
        userRepository.save(user);
    }

    // ✅ NEW METHOD – GET ALL USERS
    @Override
    public List<UserDTO> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // 🔹 PRIVATE MAPPER (BEST PRACTICE)
    private UserDTO mapToDTO(User user) {

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getRole().getName(),   // ROLE_ADMIN / ROLE_CUSTOMER / ROLE_EMPLOYEE
                user.isActive()
        );
    }
}