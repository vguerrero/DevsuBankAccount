package com.bank.account.BankAccount.repository;

import com.bank.account.BankAccount.model.Account;
import com.bank.account.BankAccount.model.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Long> {

    @Query( "select o from AccountTransaction o where clientId in :clientid" )
    List<AccountTransaction> findAccountTransactionsByClientId(@Param("clientid") Long clientId);

    Long deleteByClientId(Long clientId);
}
