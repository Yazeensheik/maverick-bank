package com.wipro.maverick_bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.maverick_bank.entity.CustomerProfile;

public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, Long> {
}