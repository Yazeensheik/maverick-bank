package com.wipro.maverick_bank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.maverick_bank.entity.CustomerProfile;
import com.wipro.maverick_bank.entity.User;

@Repository
public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, Long> {

    Optional<CustomerProfile> findByUser(User user);

    Optional<CustomerProfile> findByUserUsername(String username);
}