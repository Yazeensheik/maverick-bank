package com.wipro.maverick_bank.service;

import java.util.List;

import com.wipro.maverick_bank.dto.BeneficiaryDTO;

public interface BeneficiaryService {

	BeneficiaryDTO addBeneficiary(BeneficiaryDTO beneficiaryDTO);

	BeneficiaryDTO getBeneficiaryById(Long beneficiaryId);

	List<BeneficiaryDTO> getAllBeneficiaries();

	void deleteBeneficiary(Long beneficiaryId);
}