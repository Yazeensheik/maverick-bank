package com.wipro.maverick_bank.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wipro.maverick_bank.dto.EmployeeProfileDTO;
import com.wipro.maverick_bank.entity.Role;
import com.wipro.maverick_bank.entity.User;
import com.wipro.maverick_bank.exception.ResourceNotFoundException;
import com.wipro.maverick_bank.repository.EmployeeProfileRepository;
import com.wipro.maverick_bank.repository.RoleRepository;
import com.wipro.maverick_bank.repository.UserRepository;

@SpringBootTest
class EmployeeProfileServiceImplTest {

    @Autowired
    private EmployeeProfileService employeeProfileService;

    @Autowired
    private EmployeeProfileRepository employeeProfileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private User savedUser;

    @BeforeEach
    void setUp() {
        employeeProfileRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();

        Role role = new Role();
        role.setName("EMPLOYEE");
        Role savedRole = roleRepository.save(role);

        User user = new User();
        user.setUsername("employee1");
        user.setPassword("12345");
        user.setActive(true);
        user.setRole(savedRole);

        savedUser = userRepository.save(user);
    }

    @Test
    void testCreateEmployeeProfile() {
        EmployeeProfileDTO dto = new EmployeeProfileDTO();
        dto.setFullName("Ajay Kumar");
        dto.setEmail("ajay@gmail.com");
        dto.setPhone("9876543210");
        dto.setDepartment("IT");
        dto.setUserId(savedUser.getId());

        EmployeeProfileDTO result = employeeProfileService.createEmployeeProfile(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("Ajay Kumar", result.getFullName());
        assertEquals("ajay@gmail.com", result.getEmail());
        assertEquals("9876543210", result.getPhone());
        assertEquals("IT", result.getDepartment());
        assertEquals(savedUser.getId(), result.getUserId());
    }



    @Test
    void testDeleteEmployeeProfile() {
        EmployeeProfileDTO dto = new EmployeeProfileDTO();
        dto.setFullName("Delete Me");
        dto.setEmail("delete@gmail.com");
        dto.setPhone("7777777777");
        dto.setDepartment("Testing");
        dto.setUserId(savedUser.getId());

        EmployeeProfileDTO created = employeeProfileService.createEmployeeProfile(dto);

        employeeProfileService.deleteEmployeeProfile(created.getId());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            employeeProfileService.getEmployeeProfileById(created.getId());
        });

        assertEquals("Employee profile not found with id: " + created.getId(), exception.getMessage());
    }

}