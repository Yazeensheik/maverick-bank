package com.wipro.maverick_bank.entity;

import com.wipro.maverick_bank.entity.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long userId;
	private String name;
	
	@Column(unique=true, nullable=false)
	private String email; //LOGIN-ID
	@Column(nullable=false)
	private String password;
	private String status; //ACTIVE, INACTIVE
	
	@ManyToOne
	@JoinColumn(name="role_id")
	private Role role;  //CUSTOMER, EMPLOYEE, ADMIN
}
