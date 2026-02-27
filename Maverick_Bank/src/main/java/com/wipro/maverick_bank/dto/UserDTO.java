package com.wipro.maverick_bank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

<<<<<<< HEAD
	private Long userId;
	private String name;
	private String email;
	private String role;
	private String status;
}
=======
	private Long id;
	private String username;
	private String role;
	private boolean active;

	public UserDTO() {
	}

	public UserDTO(Long id, String username, String role, boolean active) {
		this.id = id;
		this.username = username;
		this.role = role;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
>>>>>>> 381ada5458f058c6444d65892a1fc73057053185
