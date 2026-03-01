package com.wipro.maverick_bank.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.wipro.maverick_bank.dto.AccountDTO;
import com.wipro.maverick_bank.dto.BeneficiaryDTO;
import com.wipro.maverick_bank.entity.CustomerProfile;
import com.wipro.maverick_bank.repository.CustomerProfileRepository;

@SpringBootTest
@Transactional
class BeneficiaryServiceTest {

	@Autowired
	private BeneficiaryService beneficiaryService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private CustomerProfileRepository customerProfileRepository;

	private Long createAccount() {

		CustomerProfile cp = new CustomerProfile();
		cp.setFullName("Beneficiary Customer");
		cp.setEmail("beneficiary@email.com");
		cp.setPhone("8888888888");

		cp = customerProfileRepository.save(cp);

		AccountDTO account = accountService
				.createAccount(new AccountDTO("ACC20001", "SAVINGS", 7000.0, "ACTIVE", null, cp.getId()));

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