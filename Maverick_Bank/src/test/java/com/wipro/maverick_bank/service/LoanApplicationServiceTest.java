package com.wipro.maverick_bank.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.wipro.maverick_bank.dto.LoanApplicationDTO;
import com.wipro.maverick_bank.entity.CustomerProfile;
import com.wipro.maverick_bank.entity.Loan;
import com.wipro.maverick_bank.entity.Role;
import com.wipro.maverick_bank.entity.User;
import com.wipro.maverick_bank.repository.CustomerProfileRepository;
import com.wipro.maverick_bank.repository.LoanApplicationRepository;
import com.wipro.maverick_bank.repository.LoanRepository;
import com.wipro.maverick_bank.repository.RoleRepository;
import com.wipro.maverick_bank.repository.UserRepository;

@SpringBootTest
@ActiveProfiles("test")
class LoanApplicationServiceTest {

    @Autowired
    private LoanApplicationService loanApplicationService;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CustomerProfileRepository profileRepository;

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    private User user;
    private Loan loan;

    @BeforeEach
    void setup() {

        loanApplicationRepository.deleteAll();
        profileRepository.deleteAll();
        userRepository.deleteAll();
        loanRepository.deleteAll();
        roleRepository.deleteAll();

        // Create role
        Role role = new Role();
        role.setName("CUSTOMER");
        roleRepository.save(role);

        // Create user
        user = new User();
        user.setUsername("loanuser@test.com");
        user.setPassword("Test1234");
        user.setRole(role);
        user.setActive(true);
        userRepository.save(user);

        // Create customer profile
        CustomerProfile profile = new CustomerProfile();
        profile.setUser(user);
        profileRepository.save(profile);

        // Create loan
        loan = new Loan();
        loan.setLoanType("HOME_LOAN");
        loan.setInterestRate(8.5);
        loan.setMinAmount(100000.0);
        loan.setMaxAmount(5000000.0);
        loan.setTenureInMonths(240);
        loanRepository.save(loan);
    }

    // ============================================
    // TEST APPLY FOR LOAN
    // ============================================
    @Test
    void testApplyForLoan() {

        LoanApplicationDTO dto = new LoanApplicationDTO();
        dto.setLoanType(loan.getLoanType());
        dto.setAmount(500000.0);
        dto.setPurpose("House purchase");

        LoanApplicationDTO result =
                loanApplicationService.applyForLoan(user.getUsername(), dto);

        assertNotNull(result);
        assertEquals(loan.getLoanType(), result.getLoanType());
        assertEquals(500000.0, result.getAmount());
        assertEquals("House purchase", result.getPurpose());

        // Verify saved in database
        assertEquals(1, loanApplicationRepository.count());
    }
}