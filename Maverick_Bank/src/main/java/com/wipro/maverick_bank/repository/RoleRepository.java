package com.wipro.maverick_bank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.maverick_bank.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    // Used to fetch CUSTOMER / EMPLOYEE / ADMIN roles
    Optional<Role> findByName(String name);
}
