package com.wipro.maverick_bank.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.wipro.maverick_bank.dto.LoanApplicationDTO;
import com.wipro.maverick_bank.entity.Loan;
import com.wipro.maverick_bank.entity.Role;
import com.wipro.maverick_bank.entity.User;
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
    private LoanApplicationRepository loanApplicationRepository;

    private Loan savedLoan;
    private User savedUser;

    @BeforeEach
    void setup() {

        loanApplicationRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
        loanRepository.deleteAll();

        // Create Role
        Role role = new Role();
        role.setName("CUSTOMER");
        role = roleRepository.save(role);

        // Create User
        User user = new User();
        user.setUsername("testuser@example.com");
        user.setPassword("1234");
        user.setRole(role);
        user.setActive(true);
        savedUser = userRepository.save(user);

        // Create Loan
        Loan loan = new Loan();
        loan.setLoanType("HOME_LOAN");
        loan.setInterestRate(8.5);
        loan.setMinAmount(100000);
        loan.setMaxAmount(5000000);
        loan.setTenureInMonths(240);
        savedLoan = loanRepository.save(loan);
    }

    // ============================================
    // TEST APPLY FOR LOAN
    // ============================================
    @Test
    void testApplyForLoan() {

        LoanApplicationDTO dto = new LoanApplicationDTO(
                savedLoan.getLoanId(),
                200000,
                "House Construction"
        );

        LoanApplicationDTO response =
                loanApplicationService.applyForLoan(savedUser.getId(), dto);

        // Verify response
        assertNotNull(response);
        assertEquals(savedLoan.getLoanId(), response.getLoanId());
        assertEquals(200000, response.getAmount());
        assertEquals("House Construction", response.getPurpose());

        // Verify DB state
        assertEquals(1, loanApplicationRepository.count());

        var application = loanApplicationRepository.findAll().get(0);

        assertEquals("PENDING", application.getStatus());
        assertNotNull(application.getAppliedDate());
        assertNull(application.getApprovedDate());
        assertEquals(savedUser.getId(), application.getCustomer().getId());
        assertEquals(savedLoan.getLoanId(), application.getLoan().getLoanId());
    }
}