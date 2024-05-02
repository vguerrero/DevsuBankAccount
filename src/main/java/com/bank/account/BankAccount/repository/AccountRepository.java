package com.bank.account.BankAccount.repository;

import com.bank.account.BankAccount.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    Account findAccountByNumber(String number);
}
