package com.wipro.maverick_bank.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.maverick_bank.dto.AccountDTO;
import com.wipro.maverick_bank.entity.Account;
import com.wipro.maverick_bank.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public AccountDTO createAccount(AccountDTO dto) {

		Account account = new Account();
		account.setAccountNumber(dto.getAccountNumber());
		account.setAccountType(dto.getAccountType());
		account.setBalance(dto.getBalance());
		account.setStatus(dto.getStatus());
		account.setAccountId(dto.getAccountId());

		Account saved = accountRepository.save(account);

		return new AccountDTO(saved.getAccountNumber(), saved.getAccountType(), saved.getBalance(), saved.getStatus(),
				saved.getAccountId());
	}

	@Override
	public AccountDTO getAccountById(Long id) {

		Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));

		return new AccountDTO(account.getAccountNumber(), account.getAccountType(), account.getBalance(),
				account.getStatus(), account.getAccountId());
	}

	@Override
	public List<AccountDTO> getAllAccounts() {

		return accountRepository.findAll().stream().map(a -> new AccountDTO(a.getAccountNumber(), a.getAccountType(),
				a.getBalance(), a.getStatus(), a.getAccountId())).collect(Collectors.toList());
	}

	@Override
	public AccountDTO updateAccount(Long id, AccountDTO dto) {

		Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));

		account.setAccountType(dto.getAccountType());
		account.setBalance(dto.getBalance());
		account.setStatus(dto.getStatus());

		Account updated = accountRepository.save(account);

		return new AccountDTO(updated.getAccountNumber(), updated.getAccountType(), updated.getBalance(),
				updated.getStatus(), updated.getAccountId());
	}

	@Override
	public void deleteAccount(Long id) {

		accountRepository.deleteById(id);
	}
}