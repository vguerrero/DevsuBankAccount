package com.bank.account.BankAccount.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name="atransaction")
public class AccountTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long clientId;

    private String accountNumber;

    private Date date;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private double value;

    private double balance;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
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

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }



    @Override
    public String toString() {
        return "AccountTransaction{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", accountNumber='" + accountNumber + '\'' +
                ", date=" + date +
                ", transactionType=" + transactionType +
                ", value=" + value +
                ", balance=" + balance +
                '}';
    }
}
