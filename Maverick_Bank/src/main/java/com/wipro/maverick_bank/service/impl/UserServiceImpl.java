package com.wipro.maverick_bank.service.impl;

<<<<<<< HEAD
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wipro.maverick_bank.dto.AdminCreateUserDTO;
=======
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wipro.maverick_bank.dto.CreateUserRequestDTO;
>>>>>>> 381ada5458f058c6444d65892a1fc73057053185
import com.wipro.maverick_bank.dto.UserDTO;
import com.wipro.maverick_bank.entity.Role;
import com.wipro.maverick_bank.entity.User;
import com.wipro.maverick_bank.repository.RoleRepository;
import com.wipro.maverick_bank.repository.UserRepository;
<<<<<<< HEAD
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
=======
import com.wipro.maverick_bank.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO createCustomer(CreateUserRequestDTO request) {

        Role role = roleRepository.findByName("CUSTOMER")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);
        user.setActive(true);

        user = userRepository.save(user);

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                role.getName(),
                user.isActive()
        );
    }

    @Override
    public UserDTO createEmployee(CreateUserRequestDTO request) {

        Role role = roleRepository.findByName("EMPLOYEE")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);
        user.setActive(true);

        user = userRepository.save(user);

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                role.getName(),
                user.isActive()
>>>>>>> 381ada5458f058c6444d65892a1fc73057053185
        );
    }

    @Override
    public UserDTO getUserById(Long userId) {
<<<<<<< HEAD
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
=======
>>>>>>> 381ada5458f058c6444d65892a1fc73057053185

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

<<<<<<< HEAD
        user.setStatus(status);

        User updatedUser = userRepository.save(user);

        return new UserDTO(
                updatedUser.getUserId(),
                updatedUser.getName(),
                updatedUser.getEmail(),
                updatedUser.getRole().getRoleName(),
                updatedUser.getStatus()
=======
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getRole().getName(),
                user.isActive()
>>>>>>> 381ada5458f058c6444d65892a1fc73057053185
        );
    }

    @Override
<<<<<<< HEAD
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

}
=======
    public void deactivateUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setActive(false);
        userRepository.save(user);
    }
}
>>>>>>> 381ada5458f058c6444d65892a1fc73057053185
