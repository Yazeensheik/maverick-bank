package com.wipro.maverick_bank.entity;

import jakarta.persistence.*;

@Entity
<<<<<<< Updated upstream
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // CUSTOMER, EMPLOYEE, ADMIN

    public Role() {
    }

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
=======
@Table(name="roles")
public class Role{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long roleId;
	
	@Column(unique=true, nullable=false)
	private String roleName; //CUSTOMER, EMPLOYEE, ADMIN
	
	@OneToMany(mappedBy="role")
	private List<User> users;

	/*
	 * @Table(name = "roles") public class Role {
	 * 
	 * @Id
	 * 
	 * @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
	 * 
	 * @Column(unique = true, nullable = false) private String name; // CUSTOMER,
	 * EMPLOYEE, ADMIN
	 * 
	 * public Role() {}
	 * 
	 * public Role(String name) { this.name = name; }
	 * 
	 * public Long getId() { return id; }
	 * 
	 * public String getName() { return name; }
	 * 
	 * public void setName(String name) { this.name = name; }
	 */
>>>>>>> Stashed changes
}