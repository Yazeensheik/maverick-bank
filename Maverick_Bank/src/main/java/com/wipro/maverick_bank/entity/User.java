package com.wipro.maverick_bank.entity;

<<<<<<< HEAD
import com.wipro.maverick_bank.entity.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
=======
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
>>>>>>> 381ada5458f058c6444d65892a1fc73057053185
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
<<<<<<< HEAD
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
=======
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
>>>>>>> 381ada5458f058c6444d65892a1fc73057053185

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User {

<<<<<<< HEAD
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
=======
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private boolean active = true;

    /* MANY users → ONE role */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    /* ONE user → ONE customer profile */
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private CustomerProfile customerProfile;

    /* ONE user → ONE employee profile */
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private EmployeeProfile employeeProfile;

    public User() {}

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.active = true;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }

    public CustomerProfile getCustomerProfile() {
        return customerProfile;
    }

    public EmployeeProfile getEmployeeProfile() {
        return employeeProfile;
    }
}
>>>>>>> 381ada5458f058c6444d65892a1fc73057053185
