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
    void testGetEmployeeProfileById() {
        EmployeeProfileDTO dto = new EmployeeProfileDTO();
        dto.setFullName("Ajay");
        dto.setEmail("ajay@gmail.com");
        dto.setPhone("9999999999");
        dto.setDepartment("HR");
        dto.setUserId(savedUser.getId());

        EmployeeProfileDTO created = employeeProfileService.createEmployeeProfile(dto);

        EmployeeProfileDTO result = employeeProfileService.getEmployeeProfileById(created.getId());

        assertNotNull(result);
        assertEquals(created.getId(), result.getId());
        assertEquals("Ajay", result.getFullName());
        assertEquals("HR", result.getDepartment());
    }

    @Test
    void testGetAllEmployeeProfiles() {
        EmployeeProfileDTO dto1 = new EmployeeProfileDTO();
        dto1.setFullName("Ajay");
        dto1.setEmail("ajay@gmail.com");
        dto1.setPhone("9999999999");
        dto1.setDepartment("IT");
        dto1.setUserId(savedUser.getId());

        EmployeeProfileDTO dto2 = new EmployeeProfileDTO();
        dto2.setFullName("Kumar");
        dto2.setEmail("kumar@gmail.com");
        dto2.setPhone("8888888888");
        dto2.setDepartment("Finance");
        dto2.setUserId(savedUser.getId());

        employeeProfileService.createEmployeeProfile(dto1);
        employeeProfileService.createEmployeeProfile(dto2);

        List<EmployeeProfileDTO> list = employeeProfileService.getAllEmployeeProfiles();

        assertNotNull(list);
        assertEquals(2, list.size());
    }

    @Test
    void testUpdateEmployeeProfile() {
        EmployeeProfileDTO dto = new EmployeeProfileDTO();
        dto.setFullName("Old Name");
        dto.setEmail("old@gmail.com");
        dto.setPhone("9999999999");
        dto.setDepartment("Support");
        dto.setUserId(savedUser.getId());

        EmployeeProfileDTO created = employeeProfileService.createEmployeeProfile(dto);

        EmployeeProfileDTO updateDto = new EmployeeProfileDTO();
        updateDto.setFullName("New Name");
        updateDto.setEmail("new@gmail.com");
        updateDto.setPhone("8888888888");
        updateDto.setDepartment("Admin");

        EmployeeProfileDTO updated = employeeProfileService.updateEmployeeProfile(created.getId(), updateDto);

        assertNotNull(updated);
        assertEquals("New Name", updated.getFullName());
        assertEquals("new@gmail.com", updated.getEmail());
        assertEquals("8888888888", updated.getPhone());
        assertEquals("Admin", updated.getDepartment());
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

    @Test
    void testCreateEmployeeProfile_UserNotFound() {
        EmployeeProfileDTO dto = new EmployeeProfileDTO();
        dto.setFullName("Invalid User");
        dto.setEmail("invalid@gmail.com");
        dto.setPhone("6666666666");
        dto.setDepartment("IT");
        dto.setUserId(999L);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            employeeProfileService.createEmployeeProfile(dto);
        });

        assertEquals("User not found with id: 999", exception.getMessage());
    }

    @Test
    void testGetEmployeeProfileById_NotFound() {
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            employeeProfileService.getEmployeeProfileById(999L);
        });

        assertEquals("Employee profile not found with id: 999", exception.getMessage());
    }

    @Test
    void testUpdateEmployeeProfile_NotFound() {
        EmployeeProfileDTO dto = new EmployeeProfileDTO();
        dto.setFullName("Test");
        dto.setEmail("test@gmail.com");
        dto.setPhone("9999999999");
        dto.setDepartment("IT");

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            employeeProfileService.updateEmployeeProfile(999L, dto);
        });

        assertEquals("Employee profile not found with id: 999", exception.getMessage());
    }

    @Test
    void testDeleteEmployeeProfile_NotFound() {
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            employeeProfileService.deleteEmployeeProfile(999L);
        });

        assertEquals("Employee profile not found with id: 999", exception.getMessage());
    }
}