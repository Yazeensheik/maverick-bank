package com.wipro.maverick_bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    // =====================================================
    // CREATE USER (CUSTOMER / EMPLOYEE)
    // =====================================================
    @Override
    public UserDTO createUser(CreateUserRequestDTO request, String roleName) {

        // Check if user already exists
        if (userRepository.existsByUsername(request.getEmail())) {
            throw new RuntimeException("User with this email already exists");
        }

        // Fetch role
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role " + roleName + " not found"));

        // Create user entity
        User user = new User();
        user.setUsername(request.getEmail());   // email used as username
        user.setPassword(request.getPassword()); // PLAIN TEXT PASSWORD
        user.setRole(role);
        user.setActive(true);

        // Save user
        user = userRepository.save(user);

        // Return DTO
        return mapToDTO(user);
    }

    // =====================================================
    // GET USER BY ID
    // =====================================================
    @Override
    public UserDTO getUserById(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + userId));

        return mapToDTO(user);
    }

    // =====================================================
    // DEACTIVATE USER (SOFT DELETE)
    // =====================================================
    @Override
    public void deactivateUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + userId));

        user.setActive(false);
        userRepository.save(user);
    }

    // =====================================================
    // GET ALL USERS
    // =====================================================
    @Override
    public List<UserDTO> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // =====================================================
    // ENTITY → DTO MAPPER
    // =====================================================
    private UserDTO mapToDTO(User user) {

        return new UserDTO(
                user.getId(),
                user.getUsername(),        // email
                user.getRole().getName(),  // CUSTOMER / EMPLOYEE / ADMIN
                user.isActive()
        );
    }
}