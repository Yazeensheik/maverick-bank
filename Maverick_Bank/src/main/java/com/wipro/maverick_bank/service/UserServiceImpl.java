package com.wipro.maverick_bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wipro.maverick_bank.dto.CreateUserRequestDTO;
import com.wipro.maverick_bank.dto.UserDTO;
import com.wipro.maverick_bank.entity.Role;
import com.wipro.maverick_bank.entity.User;
import com.wipro.maverick_bank.repository.RoleRepository;
import com.wipro.maverick_bank.repository.UserRepository;
import com.wipro.maverick_bank.service.UserService;

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

        Role role = roleRepository.findByName("CUSTOMER")
                .orElseThrow(() -> new RuntimeException("Role CUSTOMER not found"));

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);
        user.setActive(true);

        user = userRepository.save(user);

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                role.getName(),
                user.isActive()
        );
    }

    @Override
    public UserDTO createEmployee(CreateUserRequestDTO request) {

        Role role = roleRepository.findByName("EMPLOYEE")
                .orElseThrow(() -> new RuntimeException("Role EMPLOYEE not found"));

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);
        user.setActive(true);

        user = userRepository.save(user);

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                role.getName(),
                user.isActive()
        );
    }

    @Override
    public UserDTO getUserById(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getRole().getName(),
                user.isActive()
        );
    }

    @Override
    public void deactivateUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setActive(false);
        userRepository.save(user);
    }
}