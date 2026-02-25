package com.wipro.maverick_bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.maverick_bank.entity.EmployeeProfile;

public interface EmployeeProfileRepository extends JpaRepository<EmployeeProfile, Long> {
}