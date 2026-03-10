package com.wipro.maverick_bank.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wipro.maverick_bank.dto.CustomerProfileDTO;
import com.wipro.maverick_bank.entity.Role;
import com.wipro.maverick_bank.entity.User;
import com.wipro.maverick_bank.repository.CustomerProfileRepository;
import com.wipro.maverick_bank.repository.RoleRepository;
import com.wipro.maverick_bank.repository.UserRepository;
import com.wipro.maverick_bank.service.CustomerProfileService;

@SpringBootTest
class CustomerProfileServiceImplTest {

    @Autowired
    private CustomerProfileService customerProfileService;

    @Autowired
    private CustomerProfileRepository customerProfileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private User savedUser;

    @BeforeEach
    void setup() {

        customerProfileRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();

        // create role
        Role role = new Role();
        role.setName("CUSTOMER");
        Role savedRole = roleRepository.save(role);

        // create user
        User user = new User();
        user.setUsername("ajay");
        user.setPassword("12345");
        user.setActive(true);
        user.setRole(savedRole);

        savedUser = userRepository.save(user);
    }

    @Test
    void testCreateCustomerProfile() {

        CustomerProfileDTO dto = new CustomerProfileDTO();
        dto.setFullName("Ajay Kumar");
        dto.setEmail("ajay@gmail.com");
        dto.setPhone("9876543210");
        dto.setUserId(savedUser.getId());

        CustomerProfileDTO result = customerProfileService.createCustomerProfile(dto);

        assertNotNull(result);
        assertEquals("Ajay Kumar", result.getFullName());
        assertEquals(savedUser.getId(), result.getUserId());
    }

   
    @Test
    void testDeleteCustomerProfile() {

        CustomerProfileDTO dto = new CustomerProfileDTO();
        dto.setFullName("Delete Test");
        dto.setEmail("delete@gmail.com");
        dto.setPhone("7777777777");
        dto.setUserId(savedUser.getId());

        CustomerProfileDTO created = customerProfileService.createCustomerProfile(dto);

        customerProfileService.deleteCustomerProfile(created.getId());

        assertThrows(RuntimeException.class,
                () -> customerProfileService.getCustomerProfileById(created.getId()));
    }
}