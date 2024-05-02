package com.bank.account.BankAccount.service;

import com.bank.account.BankAccount.model.Account;
import com.bank.account.BankAccount.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService{

    AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account createAccount(Account c) {
       return accountRepository.save(c);
    }

    @Override
    public void deleteAccount(long accId) {
        Account c = getAccount(accId);
        if(c != null)
            accountRepository.delete(c);
    }

    private Account getAccount(long accId) {
        Account c = accountRepository.getById(accId);
        return c;
    }

    @Override
    public Account updateAccount(Account c) {
        Account f = getAccount(c.getId());
        if(f != null){
            f.setBalance(c.getBalance());
            f.setNumber(c.getNumber());
            f.setType(c.getType());
            f.setState(c.isState());
            accountRepository.save(f);
        }
        return null;
    }

    @Override
    public Account getAccount(String accountNumber) {
        return accountRepository.findAccountByNumber(accountNumber);
    }

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }
}
