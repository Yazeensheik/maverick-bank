package com.wipro.maverick_bank.controller;


import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.maverick_bank.dto.CreateUserRequestDTO;
import com.wipro.maverick_bank.dto.UserDTO;
import com.wipro.maverick_bank.entity.Role;
import com.wipro.maverick_bank.entity.User;
import com.wipro.maverick_bank.repository.RoleRepository;
import com.wipro.maverick_bank.repository.UserRepository;
import com.wipro.maverick_bank.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    
    
    
    @PostMapping("/login")
    public User login(@RequestBody User loginRequest) {

        User user = userRepository.findByUsernameAndActiveTrue(loginRequest.getUsername());

        if(user == null) {
            throw new RuntimeException("User not found");
        }

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }

    /**
     * Add Customer
     * Accessible by: ADMIN
     */
    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add/customer")
    public ResponseEntity<UserDTO> createCustomer(
            @Valid @RequestBody CreateUserRequestDTO request) {

        UserDTO user = userService.createUser(request, "CUSTOMER");
        return ResponseEntity.ok(user);
    }

    /**
     * Add Bank Employee
     * Accessible by: ADMIN
     */
    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add/employee")
    public ResponseEntity<UserDTO> createEmployee(
            @Valid @RequestBody CreateUserRequestDTO request) {

        UserDTO user = userService.createUser(request, "EMPLOYEE");
        return ResponseEntity.ok(user);
    }

    /**
     * Get User by ID
     * Accessible by: ADMIN, EMPLOYEE
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @GetMapping("/get/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {

        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Get All Users
     * Accessible by: ADMIN
     */
    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {

        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Deactivate User (Soft Delete)
     * Accessible by: ADMIN
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/deactivate/{id}")
    public ResponseEntity<String> deactivateUser(@PathVariable Long id) {

        userService.deactivateUser(id);
        return ResponseEntity.ok("User deactivated successfully");
    }

    /**
     * Permanently Delete User
     * Accessible by: ADMIN
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}