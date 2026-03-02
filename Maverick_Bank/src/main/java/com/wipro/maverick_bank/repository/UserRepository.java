package com.wipro.maverick_bank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.maverick_bank.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Used during login (AuthService)
    Optional<User> findByUsername(String username);
}
