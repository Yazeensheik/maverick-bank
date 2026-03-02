package com.wipro.maverick_bank.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wipro.maverick_bank.dto.AccountDTO;
import com.wipro.maverick_bank.dto.BeneficiaryDTO;
import com.wipro.maverick_bank.entity.CustomerProfile;
import com.wipro.maverick_bank.entity.Role;
import com.wipro.maverick_bank.entity.User;
import com.wipro.maverick_bank.repository.CustomerProfileRepository;
import com.wipro.maverick_bank.repository.RoleRepository;
import com.wipro.maverick_bank.repository.UserRepository;

@SpringBootTest
//@Transactional
class BeneficiaryServiceTest {
	
	@Autowired
	private BeneficiaryService beneficiaryService;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private CustomerProfileRepository customerProfileRepository;

	@Autowired
	private AccountService accountService;

	private Long createAccount() {

		Role role = roleRepository.findByName("CUSTOMER").orElseGet(() -> roleRepository.save(new Role("CUSTOMER")));
		User user = new User();
		user.setUsername("karan");
		user.setPassword("password");
		user.setActive(true);
		user.setRole(role);
		user = userRepository.save(user);

		CustomerProfile cp = new CustomerProfile();
		cp.setFullName("Karan Maan");
		cp.setEmail("karan@email.com");
		cp.setPhone("8888888888");
		cp.setUser(user);
		cp = customerProfileRepository.save(cp);

		AccountDTO account = accountService
				.createAccount(new AccountDTO("ACC999002", "SAVINGS", 9000.0, "ACTIVE", null, cp.getId()));

		return account.getAccountId();
	}

	@Test
	void testAddBeneficiary() {

		Long accountId = createAccount();

		BeneficiaryDTO dto = new BeneficiaryDTO("Rahul", "1234567890", "SBI", "Hyderabad", "SBIN000123", accountId);

		BeneficiaryDTO saved = beneficiaryService.addBeneficiary(dto);

		assertNotNull(saved);
		assertEquals("Rahul", saved.getBeneficiaryName());
	}
}