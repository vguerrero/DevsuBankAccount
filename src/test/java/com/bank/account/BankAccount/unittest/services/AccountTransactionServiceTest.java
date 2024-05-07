package com.bank.account.BankAccount.unittest.services;

import com.bank.account.BankAccount.data.TestData;
import com.bank.account.BankAccount.model.Account;
import com.bank.account.BankAccount.model.AccountTransaction;
import com.bank.account.BankAccount.repository.AccountRepository;
import com.bank.account.BankAccount.repository.AccountTransactionRepository;
import com.bank.account.BankAccount.service.AccountService;
import com.bank.account.BankAccount.service.AccountServiceImpl;
import com.bank.account.BankAccount.service.AccountTransactionService;
import com.bank.account.BankAccount.service.AccountTransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AccountTransactionServiceTest {

    AccountService accountService;

    AccountTransactionService accountTransactionService;

    AccountTransactionRepository accountTransactionRepository;

    RestTemplate restTemplate;

    AccountTransaction accountTransaction1;


    @BeforeEach
    void setUp() {
        this.restTemplate=mock(RestTemplate.class);
        this.accountTransactionRepository = mock(AccountTransactionRepository.class);
        this.accountService = mock(AccountService.class);
        this.accountTransactionService = new AccountTransactionServiceImpl(accountService,accountTransactionRepository);
        this.accountTransaction1 = TestData.accountTransaction1();
    }

    @Test
    void testCreateAccount() throws Exception {
        doAnswer(invocation -> {
            AccountTransaction accountTransactionDB = invocation.getArgument(0);
            accountTransactionDB.setId(3L);
            return accountTransactionDB;
        }).when(this.accountTransactionRepository).save(any(AccountTransaction.class));

        when(this.accountService.getAccount(accountTransaction1.getAccountNumber())).thenReturn(TestData.accountList().get(0));

        AccountTransaction saved = this.accountTransactionService.createAccountTransaction(accountTransaction1);
        assertNotNull(saved);
        assertEquals(accountTransaction1.getId(), saved.getId());
        assertEquals(accountTransaction1.getTransactionType(), saved.getTransactionType());
        assertEquals(accountTransaction1.getBalance(), saved.getBalance());
    }


}
