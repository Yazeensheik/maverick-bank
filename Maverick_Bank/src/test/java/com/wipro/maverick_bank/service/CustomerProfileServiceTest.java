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
    void testGetCustomerProfileById() {

        CustomerProfileDTO dto = new CustomerProfileDTO();
        dto.setFullName("Ajay");
        dto.setEmail("ajay@gmail.com");
        dto.setPhone("9999999999");
        dto.setUserId(savedUser.getId());

        CustomerProfileDTO created = customerProfileService.createCustomerProfile(dto);

        CustomerProfileDTO result = customerProfileService.getCustomerProfileById(created.getId());

        assertEquals("Ajay", result.getFullName());
    }

    @Test
    void testGetAllCustomerProfiles() {

        CustomerProfileDTO dto1 = new CustomerProfileDTO();
        dto1.setFullName("Ajay");
        dto1.setEmail("ajay@gmail.com");
        dto1.setPhone("9999999999");
        dto1.setUserId(savedUser.getId());

        CustomerProfileDTO dto2 = new CustomerProfileDTO();
        dto2.setFullName("Kumar");
        dto2.setEmail("kumar@gmail.com");
        dto2.setPhone("8888888888");
        dto2.setUserId(savedUser.getId());

        customerProfileService.createCustomerProfile(dto1);
        customerProfileService.createCustomerProfile(dto2);

        List<CustomerProfileDTO> list = customerProfileService.getAllCustomerProfiles();

        assertEquals(2, list.size());
    }

    @Test
    void testUpdateCustomerProfile() {

        CustomerProfileDTO dto = new CustomerProfileDTO();
        dto.setFullName("Old Name");
        dto.setEmail("old@gmail.com");
        dto.setPhone("9999999999");
        dto.setUserId(savedUser.getId());

        CustomerProfileDTO created = customerProfileService.createCustomerProfile(dto);

        CustomerProfileDTO updateDto = new CustomerProfileDTO();
        updateDto.setFullName("New Name");
        updateDto.setEmail("new@gmail.com");
        updateDto.setPhone("8888888888");

        CustomerProfileDTO updated =
                customerProfileService.updateCustomerProfile(created.getId(), updateDto);

        assertEquals("New Name", updated.getFullName());
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