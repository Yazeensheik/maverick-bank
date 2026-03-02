package com.wipro.maverick_bank.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.maverick_bank.dto.AccountDTO;
import com.wipro.maverick_bank.entity.Account;
import com.wipro.maverick_bank.entity.CustomerProfile;
import com.wipro.maverick_bank.repository.AccountRepository;
import com.wipro.maverick_bank.repository.CustomerProfileRepository;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	// REQUIRED for mapping Account → CustomerProfile
	@Autowired
	private CustomerProfileRepository customerProfileRepository;

	@Override
	public AccountDTO createAccount(AccountDTO dto) {

		Account account = new Account();

		account.setAccountNumber(dto.getAccountNumber());
		account.setAccountType(dto.getAccountType());
		account.setBalance(dto.getBalance());
		account.setStatus(dto.getStatus());

		// fetch CustomerProfile before saving account
		CustomerProfile customerProfile = customerProfileRepository.findById(dto.getCustomerProfileId())
				.orElseThrow(() -> new RuntimeException("CustomerProfile not found"));

		account.setCustomerProfile(customerProfile);

		Account saved = accountRepository.save(account);

		return new AccountDTO(saved.getAccountNumber(), saved.getAccountType(), saved.getBalance(), saved.getStatus(),
				saved.getAccountId(), saved.getCustomerProfile().getId());
	}

	@Override
	public AccountDTO getAccountById(Long id) {

		Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));

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
	public AccountDTO updateAccount(Long id, AccountDTO dto) {

		Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));

		account.setAccountType(dto.getAccountType());
		account.setBalance(dto.getBalance());
		account.setStatus(dto.getStatus());

		Account updated = accountRepository.save(account);

		return new AccountDTO(updated.getAccountNumber(), updated.getAccountType(), updated.getBalance(),
				updated.getStatus(), updated.getAccountId(), updated.getCustomerProfile().getId());
	}

	@Override
	public void deleteAccount(Long id) {

		accountRepository.deleteById(id);
	}
}