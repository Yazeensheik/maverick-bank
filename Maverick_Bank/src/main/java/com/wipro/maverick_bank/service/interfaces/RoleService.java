package com.wipro.maverick_bank.service.interfaces;

import java.util.List;

import com.wipro.maverick_bank.entity.Role;

public interface RoleService {

	Role createRole(Role role);
	Role getRoleByName(String roleName);
	List<Role> getAllRoles();
}
