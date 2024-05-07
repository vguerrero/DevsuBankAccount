package com.bank.account.BankAccount.unittest.services;
import com.bank.account.BankAccount.data.TestData;
import com.bank.account.BankAccount.model.Account;
import com.bank.account.BankAccount.repository.AccountRepository;
import com.bank.account.BankAccount.repository.AccountTransactionRepository;
import com.bank.account.BankAccount.service.AccountService;
import com.bank.account.BankAccount.service.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    AccountRepository accountRepository;


    AccountTransactionRepository accountTransactionRepository;
    AccountService accountService;
    Account account1;

    @BeforeEach
    void setUp() {
        this.accountRepository = mock(AccountRepository.class);
        this.accountTransactionRepository = mock(AccountTransactionRepository.class);
        this.accountService = new AccountServiceImpl(accountRepository, accountTransactionRepository);
        this.account1 = TestData.account1();
    }

    @Test
    void testCreateAccount() throws Exception {
        doAnswer(invocation -> {
            Account accountDB = invocation.getArgument(0);
            accountDB.setId(3L);
            return accountDB;
        }).when(this.accountRepository).save(any(Account.class));

        Account saved = this.accountService.createAccount(account1);
        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals(account1.getId(), saved.getId());
        assertEquals(account1.getNumber(), saved.getNumber());
        assertEquals(account1.getBalance(), saved.getBalance());
    }

    @Test
    public void testGetAccount() {
        String accountNumber = "0013";
        when(this.accountRepository.findAccountByNumber(accountNumber)).thenReturn(TestData.accountList().get(1));
        var myAccount = this.accountRepository.findAccountByNumber(accountNumber);
        assertNotNull(myAccount.getType());
        assertEquals(myAccount.getClientId(), TestData.accountList().get(1).getClientId());
        assertEquals(myAccount.getBalance(), TestData.accountList().get(1).getBalance());
    }

    @Test
    void testDeleteAccount() throws Exception {

        doAnswer(invocation -> {
            Account accountDB = invocation.getArgument(0);
            accountDB.setId(10L);
            return accountDB;
        }).when(this.accountRepository).save(any(Account.class));
        Account saved = this.accountService.createAccount(account1);
        assertNotNull(saved);
        assertEquals(account1.getId(), saved.getId());
        //now delete the same account
        this.accountService.deleteAccount(saved.getId());
        //find it will be null because it was delete
        var myAccount = this.accountService.getAccount(account1.getNumber());
        assertNull(myAccount);
    }

    @Test
    void testUpdateAccount() {
        String accountNumber = account1.getNumber();
        when(this.accountRepository.findAccountByNumber(account1.getNumber())).thenReturn(account1);
        var myAccount = this.accountService.getAccount(account1.getNumber());
        //updating the name
        myAccount.setBalance(2500);
        this.accountService.updateAccount(myAccount);
        var accountStar = this.accountService.getAccount(account1.getNumber());
        assertEquals(accountStar.getBalance(), 2500);
    }

}
