package com.bank.account.BankAccount.service;

import com.bank.account.BankAccount.model.Account;

import java.util.List;

public interface AccountService {

    Account createAccount (Account c) throws Exception;
    void deleteAccount (long accId);
    Account updateAccount (Account c);
    Account getAccount(String accountNumber);
    List<Account> getAll();

    void deleteClientTransactions(long clientId);
}
