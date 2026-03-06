package com.wipro.maverick_bank.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.wipro.maverick_bank.dto.CreateUserRequestDTO;
import com.wipro.maverick_bank.dto.UserDTO;
import com.wipro.maverick_bank.entity.Role;
import com.wipro.maverick_bank.repository.RoleRepository;
import com.wipro.maverick_bank.repository.UserRepository;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    private Role customerRole;

    @BeforeEach
    void setup() {

        userRepository.deleteAll();
        roleRepository.deleteAll();

        customerRole = new Role();
        customerRole.setName("CUSTOMER");
        roleRepository.save(customerRole);
    }

    // ============================================
    // TEST CREATE USER
    // ============================================
    @Test
    void testCreateUser_Success() {

        CreateUserRequestDTO request = new CreateUserRequestDTO();
        request.setEmail("test@example.com");
        request.setPassword("1234");

        UserDTO savedUser = userService.createUser(request, "CUSTOMER");

        assertNotNull(savedUser);
        assertEquals("test@example.com", savedUser.getUsername());
        assertEquals("CUSTOMER", savedUser.getRole());
        assertTrue(savedUser.isActive());
    }

    // ============================================
    // TEST GET USER BY ID
    // ============================================
    @Test
    void testGetUserById() {

        CreateUserRequestDTO request = new CreateUserRequestDTO();
        request.setEmail("getuser@example.com");
        request.setPassword("1234");

        UserDTO createdUser = userService.createUser(request, "CUSTOMER");

        UserDTO fetchedUser = userService.getUserById(createdUser.getId());

        assertEquals(createdUser.getId(), fetchedUser.getId());
        assertEquals("getuser@example.com", fetchedUser.getUsername());
    }

    // ============================================
    // TEST DEACTIVATE USER
    // ============================================
    @Test
    void testDeactivateUser() {

        CreateUserRequestDTO request = new CreateUserRequestDTO();
        request.setEmail("deactivate@example.com");
        request.setPassword("1234");

        UserDTO createdUser = userService.createUser(request, "CUSTOMER");

        userService.deactivateUser(createdUser.getId());

        UserDTO updatedUser = userService.getUserById(createdUser.getId());

        assertFalse(updatedUser.isActive());
    }

    // ============================================
    // TEST GET ALL USERS
    // ============================================
    @Test
    void testGetAllUsers() {

        CreateUserRequestDTO request1 = new CreateUserRequestDTO();
        request1.setEmail("user1@example.com");
        request1.setPassword("1234");

        CreateUserRequestDTO request2 = new CreateUserRequestDTO();
        request2.setEmail("user2@example.com");
        request2.setPassword("1234");

        userService.createUser(request1, "CUSTOMER");
        userService.createUser(request2, "CUSTOMER");

        List<UserDTO> users = userService.getAllUsers();

        assertEquals(2, users.size());
    }
}