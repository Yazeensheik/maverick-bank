package com.wipro.maverick_bank.service;

import com.wipro.maverick_bank.dto.LoginRequestDTO;
import com.wipro.maverick_bank.dto.LoginResponseDTO;

public interface AuthService {

	LoginResponseDTO login(LoginRequestDTO loginRequest);
}