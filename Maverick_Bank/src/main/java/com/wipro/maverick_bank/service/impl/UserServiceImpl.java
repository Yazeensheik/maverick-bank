package com.wipro.maverick_bank.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wipro.maverick_bank.dto.AdminCreateUserDTO;
import com.wipro.maverick_bank.dto.UserDTO;
import com.wipro.maverick_bank.entity.Role;
import com.wipro.maverick_bank.entity.User;
import com.wipro.maverick_bank.repository.RoleRepository;
import com.wipro.maverick_bank.repository.UserRepository;
import com.wipro.maverick_bank.service.interfaces.UserService;


@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository= userRepository;
		this.roleRepository=roleRepository;
	}
	
	@Override
    public UserDTO createUser(AdminCreateUserDTO dto) {

        Role role = roleRepository.findByRoleName(dto.getRole())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = new User(
                null,
                dto.getName(),
                dto.getEmail(),
                dto.getPassword(),
                "ACTIVE",
                role
        );

        User savedUser = userRepository.save(user);

        return new UserDTO(
                savedUser.getUserId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRole().getRoleName(),
                savedUser.getStatus()
        );
    }

    @Override
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserDTO(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getRole().getRoleName(),
                user.getStatus()
        );
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserDTO(
                        user.getUserId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRole().getRoleName(),
                        user.getStatus()
                ))
                .collect(Collectors.toList());
    }
    
    
    @Override
    public UserDTO updateUserStatus(Long userId, String status) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setStatus(status);

        User updatedUser = userRepository.save(user);

        return new UserDTO(
                updatedUser.getUserId(),
                updatedUser.getName(),
                updatedUser.getEmail(),
                updatedUser.getRole().getRoleName(),
                updatedUser.getStatus()
        );
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

}
