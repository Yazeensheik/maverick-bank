package com.wipro.maverick_bank.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.maverick_bank.dto.BeneficiaryDTO;
import com.wipro.maverick_bank.entity.Account;
import com.wipro.maverick_bank.entity.Beneficiary;
import com.wipro.maverick_bank.repository.AccountRepository;
import com.wipro.maverick_bank.repository.BeneficiaryRepository;

@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {

	@Autowired
	private BeneficiaryRepository beneficiaryRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public BeneficiaryDTO addBeneficiary(BeneficiaryDTO dto) {

		// fetch Account for mapping
		Account account = accountRepository.findById(dto.getAccountId())
				.orElseThrow(() -> new RuntimeException("Account not found"));

		Beneficiary beneficiary = new Beneficiary();

		beneficiary.setBeneficiaryName(dto.getBeneficiaryName());
		beneficiary.setAccountNumber(dto.getAccountNumber());
		beneficiary.setBankName(dto.getBankName());
		beneficiary.setBranchName(dto.getBranchName());
		beneficiary.setIfscCode(dto.getIfscCode());

		beneficiary.setAccount(account);

		Beneficiary saved = beneficiaryRepository.save(beneficiary);

		return mapToDTO(saved);
	}

	@Override
	public BeneficiaryDTO getBeneficiaryById(Long id) {

		Beneficiary beneficiary = beneficiaryRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Beneficiary not found"));

		return mapToDTO(beneficiary);
	}

	@Override
	public List<BeneficiaryDTO> getAllBeneficiaries() {

		return beneficiaryRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	@Override
	public void deleteBeneficiary(Long id) {

		beneficiaryRepository.deleteById(id);
	}

	private BeneficiaryDTO mapToDTO(Beneficiary b) {

		return new BeneficiaryDTO(b.getBeneficiaryName(), b.getAccountNumber(), b.getBankName(), b.getBranchName(),
				b.getIfscCode(), b.getAccount().getAccountId());
	}
}