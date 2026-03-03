package com.wipro.maverick_bank.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.maverick_bank.dto.CreateUserRequestDTO;
import com.wipro.maverick_bank.dto.UserDTO;
import com.wipro.maverick_bank.entity.CustomerProfile;
import com.wipro.maverick_bank.entity.EmployeeProfile;
import com.wipro.maverick_bank.entity.Role;
import com.wipro.maverick_bank.entity.User;
import com.wipro.maverick_bank.exception.ResourceNotFoundException;
import com.wipro.maverick_bank.repository.CustomerProfileRepository;
import com.wipro.maverick_bank.repository.EmployeeProfileRepository;
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
	private CustomerProfileRepository customerProfileRepository;
	
	@Autowired
	private EmployeeProfileRepository employeeProfileRepository;
    
    // =====================================================
    // CREATE USER (CUSTOMER / EMPLOYEE)
    // =====================================================
    @Override
    public UserDTO createUser(CreateUserRequestDTO request, String roleName) {

        if (userRepository.existsByUsername(request.getEmail())) {
            throw new RuntimeException("User with this email already exists");
        }

        Role role = roleRepository.findByName("ROLE_" + roleName)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role " + roleName + " not found"));

        User user = new User();
        user.setUsername(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(role);
        user.setActive(true);

        user = userRepository.save(user);

       
        if (roleName.equalsIgnoreCase("CUSTOMER")) {

            CustomerProfile profile = new CustomerProfile();
            profile.setUser(user);
            profile.setEmail(request.getEmail());
            profile.setFullName(request.getFullName());
            profile.setPhone(request.getPhone());

            customerProfileRepository.save(profile);

        } else if (roleName.equalsIgnoreCase("EMPLOYEE")) {

            EmployeeProfile profile = new EmployeeProfile();
            profile.setUser(user);
            profile.setEmail(request.getEmail());
            profile.setFullName(request.getFullName());
            profile.setPhone(request.getPhone());

            employeeProfileRepository.save(profile);
        }

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