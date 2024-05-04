package com.bank.account.BankAccount.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.Date;

public class TransactionReportDTO {

    String clientName;

    private String accountNumber;

    private Date date;

    private String transactionType;

    private double value;

    private double balance;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public TransactionReportDTO(String clientName, String accountNumber, Date date, String transactionType, double value, double balance) {
        this.clientName = clientName;
        this.accountNumber = accountNumber;
        this.date = date;
        this.transactionType = transactionType;
        this.value = value;
        this.balance = balance;
    }
}
