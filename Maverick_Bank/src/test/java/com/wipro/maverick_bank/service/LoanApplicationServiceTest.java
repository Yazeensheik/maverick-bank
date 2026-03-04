package com.wipro.maverick_bank.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

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

    private User user;
    private Loan loan;

    @BeforeEach
    void setup() {

        profileRepository.deleteAll();
        userRepository.deleteAll();
        loanRepository.deleteAll();
        roleRepository.deleteAll();

        Role role = new Role();
        role.setName("CUSTOMER");
        roleRepository.save(role);

        user = new User();
        user.setUsername("loanuser@test.com");
        user.setPassword("1234");
        user.setRole(role);
        user.setActive(true);
        userRepository.save(user);

        CustomerProfile profile = new CustomerProfile();
        profile.setUser(user);
        profileRepository.save(profile);

        loan = new Loan();
        loan.setLoanType("HOME_LOAN");
        loan.setInterestRate(8.5);
        loan.setMinAmount(100000.0);
        loan.setMaxAmount(5000000.0);
        loan.setTenureInMonths(240);
        loanRepository.save(loan);
    }

    @Test
    void testApplyForLoan() {

        LoanApplicationDTO dto = new LoanApplicationDTO();
        dto.setLoanId(loan.getLoanId());
        dto.setAmount(500000.0);
        dto.setPurpose("House purchase");

        LoanApplicationDTO result =
                loanApplicationService.applyForLoan(user.getId(), dto);

        assertNotNull(result);
        assertEquals(loan.getLoanId(), result.getLoanId());
        assertEquals(500000.0, result.getAmount());
    }
}