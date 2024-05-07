package com.bank.account.BankAccount.service;

import com.bank.account.BankAccount.model.Account;
import com.bank.account.BankAccount.model.AccountTransaction;
import com.bank.account.BankAccount.model.TransactionReportDTO;

import java.util.List;

public interface AccountTransactionService {

    AccountTransaction createAccountTransaction (AccountTransaction c) throws Exception;
    void deleteAccountTransaction (long accTransactionId);
    AccountTransaction updateAccountTransaction (AccountTransaction c);
    List<AccountTransaction> getAll();
    public List<TransactionReportDTO> getTransactionReport(long clientId, String from, String to);
}
