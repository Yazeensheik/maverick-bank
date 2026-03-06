package com.wipro.maverick_bank.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.wipro.maverick_bank.dto.LoanDTO;
import com.wipro.maverick_bank.repository.LoanRepository;

@SpringBootTest
@ActiveProfiles("test")
class LoanServiceTest {

    @Autowired
    private LoanService loanService;

    @Autowired
    private LoanRepository loanRepository;

    @BeforeEach
    void cleanDatabase() {
        loanRepository.deleteAll();
    }

    // ============================================
    // TEST CREATE LOAN
    // ============================================
    @Test
    void testCreateLoan() {

        LoanDTO loanDTO = new LoanDTO(
                null,
                "HOME_LOAN",
                8.5,
                100000.0,
                5000000.0,
                240
        );

        LoanDTO savedLoan = loanService.createLoan(loanDTO);

        assertNotNull(savedLoan);
        assertNotNull(savedLoan.getLoanId());
        assertEquals("HOME_LOAN", savedLoan.getLoanType());
        assertEquals(8.5, savedLoan.getInterestRate());
        assertEquals(100000.0, savedLoan.getMinAmount());
        assertEquals(5000000.0, savedLoan.getMaxAmount());
        assertEquals(240, savedLoan.getTenureInMonths());

        // Verify saved in DB
        assertEquals(1, loanRepository.count());
    }
}