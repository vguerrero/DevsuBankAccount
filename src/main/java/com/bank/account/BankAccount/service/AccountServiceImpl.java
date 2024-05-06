package com.bank.account.BankAccount.service;

import com.bank.account.BankAccount.model.Account;
import com.bank.account.BankAccount.repository.AccountRepository;
import com.bank.account.BankAccount.repository.AccountTransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService{

    AccountRepository accountRepository;


    AccountTransactionRepository accountTransactionRepository;


    public AccountServiceImpl(AccountRepository accountRepository, AccountTransactionRepository accountTransactionRepository) {
        this.accountRepository = accountRepository;
        this.accountTransactionRepository=accountTransactionRepository;
    }

    @Override
    public Account createAccount(Account c) throws Exception {
        if(c != null){
            Account exist = accountRepository.findAccountByNumber(c.getNumber());
            if(exist != null){
                throw new Exception("the account already exist");
            }
        }
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

    @Override
    @Transactional
    public void deleteClientTransactions(long clientId) {
        accountRepository.deleteByClientId(clientId);
        accountTransactionRepository.deleteByClientId(clientId);
    }
}
