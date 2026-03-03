package com.wipro.maverick_bank.service;


import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.wipro.maverick_bank.entity.Role;
import com.wipro.maverick_bank.repository.RoleRepository;

@SpringBootTest
@ActiveProfiles("test")
class RoleServiceTest {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void cleanDatabase() {
        roleRepository.deleteAll();
    }

    // ============================================
    // TEST CREATE ROLE
    // ============================================
    @Test
    void testCreateRole() {

        Role role = new Role();
        role.setName("ADMIN");

        Role savedRole = roleService.createRole(role);

        assertNotNull(savedRole.getId());
        assertEquals("ADMIN", savedRole.getName());
    }

    // ============================================
    // TEST GET ROLE BY NAME
    // ============================================
    @Test
    void testGetRoleByName() {

        Role role = new Role();
        role.setName("CUSTOMER");
        roleRepository.save(role);

        Role fetchedRole = roleService.getRoleByName("CUSTOMER");

        assertEquals("CUSTOMER", fetchedRole.getName());
    }

    // ============================================
    // TEST GET ALL ROLES
    // ============================================
    @Test
    void testGetAllRoles() {

        Role role1 = new Role();
        role1.setName("CUSTOMER");

        Role role2 = new Role();
        role2.setName("EMPLOYEE");

        roleRepository.save(role1);
        roleRepository.save(role2);

        List<Role> roles = roleService.getAllRoles();

        assertEquals(2, roles.size());
    }

    // ============================================
    // TEST ROLE NOT FOUND
    // ============================================
    @Test
    void testGetRoleByName_NotFound() {

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> roleService.getRoleByName("INVALID_ROLE")
        );

        assertEquals("Role not found", exception.getMessage());
    }
}

