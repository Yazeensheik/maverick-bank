package com.wipro.maverick_bank.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.maverick_bank.dto.AccountDTO;
import com.wipro.maverick_bank.entity.Account;
import com.wipro.maverick_bank.entity.CustomerProfile;
import com.wipro.maverick_bank.exception.ResourceNotFoundException;
import com.wipro.maverick_bank.repository.AccountRepository;
import com.wipro.maverick_bank.repository.CustomerProfileRepository;
import com.wipro.maverick_bank.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Autowired
	private AccountRepository accountRepository;

	// REQUIRED for mapping Account → CustomerProfile
	@Autowired
	private CustomerProfileRepository customerProfileRepository;

	@Override
	public AccountDTO createAccount(AccountDTO dto) {

		log.info("Attempting to create account for Customer ID: {}", dto.getCustomerProfileId());

		Account account = new Account();

		account.setAccountNumber(dto.getAccountNumber());
		account.setAccountType(dto.getAccountType());
		account.setBalance(dto.getBalance());
		account.setStatus(dto.getStatus());

		// fetch CustomerProfile before saving account
		CustomerProfile customerProfile = customerProfileRepository.findById(dto.getCustomerProfileId())
				.orElseThrow(() -> {
					log.warn("CustomerProfile not found with ID: {}", dto.getCustomerProfileId());
					return new ResourceNotFoundException("CustomerProfile not found");
				});
		
		account.setCustomerProfile(customerProfile);

		Account saved = accountRepository.save(account);

		log.info("Account created successfully. Account ID: {}", saved.getAccountId());

		return new AccountDTO(saved.getAccountNumber(), saved.getAccountType(), saved.getBalance(), saved.getStatus(),
				saved.getAccountId(), saved.getCustomerProfile().getId());
	}

	@Override
	public AccountDTO getAccountById(Long id) {

		log.info("Fetching account with ID: {}", id);

		Account account = accountRepository.findById(id).orElseThrow(() -> {
			log.warn("Account not found with ID: {}", id);
			return new ResourceNotFoundException("Account not found");
		});

		return new AccountDTO(account.getAccountNumber(), account.getAccountType(), account.getBalance(),
				account.getStatus(), account.getAccountId(), account.getCustomerProfile().getId());
	}

	@Override
	public List<AccountDTO> getAllAccounts() {

		return accountRepository.findAll().stream().map(a -> new AccountDTO(a.getAccountNumber(), a.getAccountType(),
				a.getBalance(), a.getStatus(), a.getAccountId(), a.getCustomerProfile().getId()))
				.collect(Collectors.toList());
	}

	@Override
	public List<AccountDTO> getAccountsByCustomerId(Long customerId) {

		log.info("Fetching accounts for Customer ID: {}", customerId);

		List<Account> accounts = accountRepository.findByCustomerProfile_Id(customerId);

		if (accounts.isEmpty()) {
			log.warn("No accounts found for Customer ID: {}", customerId);
			throw new ResourceNotFoundException("No accounts found for customer ID: " + customerId);
		}
		return accounts.stream().map(a -> new AccountDTO(a.getAccountNumber(), a.getAccountType(), a.getBalance(),
				a.getStatus(), a.getAccountId(), a.getCustomerProfile().getId())).toList();
	}

	@Override
	public AccountDTO updateAccount(Long id, AccountDTO dto) {

		Account account = accountRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Account not found"));

		account.setAccountType(dto.getAccountType());
		account.setBalance(dto.getBalance());
		account.setStatus(dto.getStatus());

		Account updated = accountRepository.save(account);

		log.info("Account updated successfully. Account ID: {}", updated.getAccountId());

		return new AccountDTO(updated.getAccountNumber(), updated.getAccountType(), updated.getBalance(),
				updated.getStatus(), updated.getAccountId(), updated.getCustomerProfile().getId());
	}

	@Override
	public void deleteAccount(Long id) {

		if (!accountRepository.existsById(id)) {
			log.warn("Attempted to delete non-existing account. ID: {}", id);
			throw new ResourceNotFoundException("Account not found");
		}

		log.info("Deleting account with ID: {}", id);
		accountRepository.deleteById(id);
	}
}