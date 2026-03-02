package com.wipro.maverick_bank.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wipro.maverick_bank.dto.AccountDTO;
import com.wipro.maverick_bank.entity.CustomerProfile;
import com.wipro.maverick_bank.entity.Role;
import com.wipro.maverick_bank.entity.User;
import com.wipro.maverick_bank.repository.CustomerProfileRepository;
import com.wipro.maverick_bank.repository.RoleRepository;
import com.wipro.maverick_bank.repository.UserRepository;

@SpringBootTest
//@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountServiceTest {

	@Autowired
	private AccountService accountService;

	@Autowired
	private CustomerProfileRepository customerProfileRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	static Long accountId;
	static Long customerProfileId;

	private Long createCustomerProfile() {

		Role role = roleRepository.findByName("CUSTOMER").orElseGet(() -> roleRepository.save(new Role("CUSTOMER")));

		User user = new User();
		user.setUsername("rahul123");
		user.setPassword("password");
		user.setRole(role);
		user = userRepository.save(user);
		CustomerProfile cp = new CustomerProfile();
		cp.setFullName("Rahul Kumar");
		cp.setEmail("rahul@email.com");
		cp.setPhone("9999999999");
		cp.setUser(user);

		cp = customerProfileRepository.save(cp);

		return cp.getId();
	}

	@Test
	@Order(1)
	void testCreateAccount() {

		customerProfileId = createCustomerProfile();

		AccountDTO dto = new AccountDTO("ACC10001", "SAVINGS", 5000.0, "ACTIVE", null, customerProfileId);

		AccountDTO saved = accountService.createAccount(dto);

		assertNotNull(saved);
		assertEquals("ACC10001", saved.getAccountNumber());

		accountId = saved.getAccountId();
	}

	@Test
	@Order(2)
	void testGetAccountById() {

		AccountDTO account = accountService.getAccountById(accountId);

		assertNotNull(account);
		assertEquals("SAVINGS", account.getAccountType());
	}

	@Test
	@Order(3)
	void testGetAllAccounts() {

		List<AccountDTO> list = accountService.getAllAccounts();

		assertFalse(list.isEmpty());
	}

	@Test
	@Order(4)
	void testUpdateAccount() {

		AccountDTO dto = new AccountDTO("ACC10001", "CURRENT", 10000.0, "ACTIVE", accountId, customerProfileId);

		AccountDTO updated = accountService.updateAccount(accountId, dto);

		assertEquals("CURRENT", updated.getAccountType());
	}

	@Test
	@Order(5)
	void testDeleteAccount() {

		accountService.deleteAccount(accountId);

		Exception ex = assertThrows(RuntimeException.class, () -> accountService.getAccountById(accountId));

		assertEquals("Account not found", ex.getMessage());
	}
}