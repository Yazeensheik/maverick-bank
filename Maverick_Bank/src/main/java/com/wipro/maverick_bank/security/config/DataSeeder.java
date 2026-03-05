package com.wipro.maverick_bank.security.config;

import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.wipro.maverick_bank.entity.Role;
import com.wipro.maverick_bank.entity.User;
import com.wipro.maverick_bank.repository.RoleRepository;
import com.wipro.maverick_bank.repository.UserRepository;

@Component
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(RoleRepository roleRepository,
                      UserRepository userRepository,
                      PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        Optional<Role> adminRoleOpt = roleRepository.findByName("ADMIN");
        Role adminRole;

        if (adminRoleOpt.isEmpty()) {
            Role r = new Role();
            r.setName("ADMIN");
            adminRole = roleRepository.save(r);
        } else {
            adminRole = adminRoleOpt.get();
        }

        if (!userRepository.existsByUsername("admin@maverick.com")) {
            User u = new User();
            u.setUsername("admin@maverick.com");
            u.setPassword(passwordEncoder.encode("Admin@123"));
            u.setRole(adminRole);
            u.setActive(true);
            userRepository.save(u);
        }
    }
}