package com.bank.account.BankAccount.service;

import com.bank.account.BankAccount.model.Account;
import com.bank.account.BankAccount.model.AccountTransaction;
import com.bank.account.BankAccount.model.TransactionType;
import com.bank.account.BankAccount.repository.AccountTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountTransactionServiceImpl implements AccountTransactionService{

    @Autowired
    AccountService accountService;

    @Autowired
    private AccountTransactionRepository accountTransactionRepository;



    @Override
    public AccountTransaction createAccountTransaction(AccountTransaction at) throws Exception {
        AccountTransaction response = null;
        if(at != null && !at.getAccountNumber().isEmpty()){
            Account account =accountService.getAccount(at.getAccountNumber());
            if( account != null){

                double newBalance;
                at.setValue(Math.abs(at.getValue()));
                if(at.getTransactionType().equals(TransactionType.RETIRO)){
                    //validate account balance
                    if(account.getBalance() <= at.getValue()){
                        throw new Exception("Saldo no disponible ");
                    }
                    else{
                        newBalance = account.getBalance() - at.getValue();
                        account.setBalance(newBalance);
                        at.setBalance(newBalance);
                        response = accountTransactionRepository.save(at);
                        accountService.updateAccount(account);
                    }
                }
                else{
                    newBalance = account.getBalance() + at.getValue();
                    account.setBalance(newBalance);
                    at.setBalance(newBalance);
                    response = accountTransactionRepository.save(at);
                    accountService.updateAccount(account);
                }
            }

        }
        return response;
    }

    @Override
    public void deleteAccountTransaction(long accTransactionId) {
        AccountTransaction  t = accountTransactionRepository.getById(accTransactionId);
        if(t != null)
            accountTransactionRepository.delete(t);
    }

    @Override
    public AccountTransaction updateAccountTransaction(AccountTransaction c) {
        return null;
    }

    @Override
    public List<AccountTransaction> getAll() {
        return accountTransactionRepository.findAll();
    }
}
