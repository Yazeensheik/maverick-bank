package com.wipro.maverick_bank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.maverick_bank.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Used for login (Spring Security)
    Optional<User> findByUsername(String username);

    // Used to prevent duplicate users during registration
    boolean existsByUsername(String username);
}