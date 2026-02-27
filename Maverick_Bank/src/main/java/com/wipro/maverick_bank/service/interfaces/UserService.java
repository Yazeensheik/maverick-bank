package com.wipro.maverick_bank.service.interfaces;

import java.util.List;

import com.wipro.maverick_bank.dto.AdminCreateUserDTO;
import com.wipro.maverick_bank.dto.UserDTO;

public interface UserService {

	UserDTO createUser(AdminCreateUserDTO adminCreateUserDTO);
	UserDTO getUserById(Long userId);
	List<UserDTO> getAllUsers();
	UserDTO updateUserStatus(Long userId, String status);
	void deleteUser(Long userId);
}
