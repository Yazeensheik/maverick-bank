package com.wipro.maverick_bank.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.maverick_bank.dto.BeneficiaryDTO;
import com.wipro.maverick_bank.entity.Account;
import com.wipro.maverick_bank.entity.Beneficiary;
import com.wipro.maverick_bank.exception.ResourceNotFoundException;
import com.wipro.maverick_bank.repository.AccountRepository;
import com.wipro.maverick_bank.repository.BeneficiaryRepository;
import com.wipro.maverick_bank.service.BeneficiaryService;

@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {

	private static final Logger log = LoggerFactory.getLogger(BeneficiaryServiceImpl.class);

	@Autowired
	private BeneficiaryRepository beneficiaryRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public BeneficiaryDTO addBeneficiary(BeneficiaryDTO dto) {

		log.info("Attempting to add beneficiary for Account ID: {}", dto.getAccountId());

		Account account = accountRepository.findById(dto.getAccountId()).orElseThrow(() -> {
			log.warn("Account not found while adding beneficiary. Account ID: {}", dto.getAccountId());
			return new ResourceNotFoundException("Account not found");
		});

		Beneficiary beneficiary = new Beneficiary();

		beneficiary.setBeneficiaryName(dto.getBeneficiaryName());
		beneficiary.setAccountNumber(dto.getAccountNumber());
		beneficiary.setBankName(dto.getBankName());
		beneficiary.setBranchName(dto.getBranchName());
		beneficiary.setIfscCode(dto.getIfscCode());

		beneficiary.setAccount(account);

		Beneficiary saved = beneficiaryRepository.save(beneficiary);

		log.info("Beneficiary added successfully with ID: {} for Account ID: {}", saved.getBeneficiaryId(),
				saved.getAccount().getAccountId());

		return mapToDTO(saved);
	}

	@Override
	public BeneficiaryDTO getBeneficiaryById(Long id) {

		log.info("Fetching beneficiary with ID: {}", id);

		Beneficiary beneficiary = beneficiaryRepository.findById(id).orElseThrow(() -> {
			log.warn("Beneficiary not found with ID: {}", id);
			return new ResourceNotFoundException("Beneficiary not found");
		});
		return mapToDTO(beneficiary);
	}

	@Override
	public List<BeneficiaryDTO> getAllBeneficiaries() {

		log.info("Fetching all beneficiaries");
		return beneficiaryRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	@Override
	public void deleteBeneficiary(Long id) {

		if (!beneficiaryRepository.existsById(id)) {
			log.warn("Attempted to delete non-existing beneficiary. ID: {}", id);
			throw new ResourceNotFoundException("Beneficiary not found");
		}

		log.info("Deleting beneficiary with ID: {}", id);
		beneficiaryRepository.deleteById(id);
	}

	private BeneficiaryDTO mapToDTO(Beneficiary b) {

		return new BeneficiaryDTO(b.getBeneficiaryName(), b.getAccountNumber(), b.getBankName(), b.getBranchName(),
				b.getIfscCode(), b.getAccount().getAccountId());
	}
}