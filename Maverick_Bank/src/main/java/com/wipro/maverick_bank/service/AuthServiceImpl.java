package com.wipro.maverick_bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wipro.maverick_bank.dto.LoginRequestDTO;
import com.wipro.maverick_bank.dto.LoginResponseDTO;
import com.wipro.maverick_bank.entity.User;
import com.wipro.maverick_bank.exception.ResourceNotFoundException;
import com.wipro.maverick_bank.repository.UserRepository;
import com.wipro.maverick_bank.security.jwt.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {

        User user = userRepository
                .findByUsername(loginRequest.getUsername())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invalid username"));

        if (!passwordEncoder.matches(
                loginRequest.getPassword(), user.getPassword())) {
            throw new ResourceNotFoundException("Invalid password");
        }

        String token = jwtUtil.generateToken(
                user.getUsername(),
                user.getRole().getName()
        );

        return new LoginResponseDTO(
                token,
                user.getId(),
                user.getRole().getName()
        );
    }
}