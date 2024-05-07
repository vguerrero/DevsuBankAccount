package com.bank.account.BankAccount.data;

import com.bank.account.BankAccount.model.Account;
import com.bank.account.BankAccount.model.AccountTransaction;
import com.bank.account.BankAccount.model.AccountType;
import com.bank.account.BankAccount.model.TransactionType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestData {

    public static Account account1(){
        Account c1 = new Account();
        c1.setType(AccountType.CORRIENTE);
        c1.setBalance(2000);
        c1.setNumber("0011");
        c1.setClientId(1L);
        return c1;
    }

    public static Account account2(){
        Account c2 = new Account();
        c2.setType(AccountType.AHORRO);
        c2.setBalance(4000);
        c2.setNumber("0013");
        c2.setClientId(2L);
        return c2;
    }

    public static List<Account> accountList(){
        List<Account> list = new ArrayList<>();
        list.add(account1());
        list.add(account2());
        return list;

    }

    public static AccountTransaction accountTransaction1(){
        AccountTransaction at = new AccountTransaction();
        at.setDate(new Date());
        at.setValue(800);
        at.setClientId(1L);
        at.setAccountNumber(account1().getNumber());
        at.setClientId(1L);
        at.setTransactionType(TransactionType.RETIRO);
        return at;
    }
}
