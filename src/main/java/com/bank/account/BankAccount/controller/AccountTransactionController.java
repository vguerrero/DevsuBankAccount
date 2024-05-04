package com.bank.account.BankAccount.controller;

import com.bank.account.BankAccount.model.Account;
import com.bank.account.BankAccount.model.AccountTransaction;
import com.bank.account.BankAccount.service.AccountTransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("movimientos")
public class AccountTransactionController {

    @Autowired
    AccountTransactionService accountTransactionService;

    private static final Logger logger = LoggerFactory.getLogger(AccountTransactionController.class);

    @PostMapping
    public ResponseEntity<?> createAccountTransaction(@RequestBody AccountTransaction accountTransaction)  {
        try {
            return new ResponseEntity<>(accountTransactionService.createAccountTransaction(accountTransaction), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<AccountTransaction>> getTransactions() {
        return new ResponseEntity<>(accountTransactionService.getAll(), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAccountTransaction(@RequestParam Long AccountTransactionId) {
        try {
            accountTransactionService.deleteAccountTransaction(AccountTransactionId);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        catch (Exception e){
            logger.error("Error api deleteAccountTransaction: {}", e.getMessage());
            return new ResponseEntity<>("Error api deleteAccountTransaction: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateAccountTransaction(@RequestBody AccountTransaction acc) {
        return new ResponseEntity<>(accountTransactionService.updateAccountTransaction(acc), HttpStatus.CREATED);
    }

}
