package com.wipro.maverick_bank.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
<<<<<<< HEAD
@Table(name="roles")
public class Role{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long roleId;
	
	@Column(unique=true, nullable=false)
	private String roleName; //CUSTOMER, EMPLOYEE, ADMIN
	
	@OneToMany(mappedBy="role")
	private List<User> users;
=======
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name; // CUSTOMER, EMPLOYEE, ADMIN

    public Role() {}

    public Role(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
>>>>>>> 381ada5458f058c6444d65892a1fc73057053185
}