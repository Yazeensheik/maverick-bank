package com.wipro.maverick_bank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminCreateUserDTO {

	private String name;
	private String email;
	private String password;
	private String role;
}
