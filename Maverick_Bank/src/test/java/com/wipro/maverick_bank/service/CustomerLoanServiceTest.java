package com.wipro.maverick_bank.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.wipro.maverick_bank.entity.CustomerLoan;
import com.wipro.maverick_bank.entity.CustomerProfile;
import com.wipro.maverick_bank.entity.Loan;
import com.wipro.maverick_bank.entity.LoanApplication;
import com.wipro.maverick_bank.entity.Role;
import com.wipro.maverick_bank.entity.User;
import com.wipro.maverick_bank.repository.CustomerProfileRepository;
import com.wipro.maverick_bank.repository.LoanApplicationRepository;
import com.wipro.maverick_bank.repository.LoanRepository;
import com.wipro.maverick_bank.repository.RoleRepository;
import com.wipro.maverick_bank.repository.UserRepository;

@SpringBootTest
@ActiveProfiles("test")
class CustomerLoanServiceTest {

    @Autowired
    private CustomerLoanService customerLoanService;

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerProfileRepository customerProfileRepository;

    @Autowired
    private RoleRepository roleRepository;

    private CustomerLoan createdLoan;

    @BeforeEach
    void setup() {

        roleRepository.deleteAll();
        userRepository.deleteAll();
        customerProfileRepository.deleteAll();
        loanRepository.deleteAll();
        loanApplicationRepository.deleteAll();

        // create role
        Role role = new Role();
        role.setName("CUSTOMER");
        roleRepository.save(role);

        // create user
        User user = new User();
        user.setUsername("loanuser@test.com");
        user.setPassword("1234");
        user.setRole(role);
        user.setActive(true);
        userRepository.save(user);

        // create profile
        CustomerProfile profile = new CustomerProfile();
        profile.setUser(user);
        customerProfileRepository.save(profile);

        // create loan product
        Loan loan = new Loan();
        loan.setLoanType("HOME_LOAN");
        loan.setInterestRate(8.5);
        loan.setMinAmount(100000);
        loan.setMaxAmount(5000000);
        loan.setTenureInMonths(240);
        loanRepository.save(loan);

        // create loan application
        LoanApplication application = new LoanApplication();
        application.setLoan(loan);
        application.setCustomer(user);
        application.setCustomerProfile(profile);
        application.setAmount(500000);
        application.setPurpose("Home purchase");
        application.setStatus("APPROVED");

        loanApplicationRepository.save(application);

        // create customer loan
        createdLoan = customerLoanService.createCustomerLoan(application.getApplicationId());
    }

    // ============================================
    // TEST GET CUSTOMER LOAN BY ID
    // ============================================
    @Test
    void testGetCustomerLoanById() {

        CustomerLoan fetchedLoan =
                customerLoanService.getCustomerLoanById(createdLoan.getCustomerLoanId());

        assertNotNull(fetchedLoan);
        assertEquals(createdLoan.getCustomerLoanId(), fetchedLoan.getCustomerLoanId());
    }
}