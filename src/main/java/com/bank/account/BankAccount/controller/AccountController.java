package com.bank.account.BankAccount.controller;


import com.bank.account.BankAccount.model.Account;
import com.bank.account.BankAccount.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cuentas")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    AccountService accountService;

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody Account account)  {
        return new ResponseEntity<>(accountService.createAccount(account), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAccounts() {
        return new ResponseEntity<>(accountService.getAll(), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAccount(@RequestParam Long accountId) {
        try {
            accountService.deleteAccount(accountId);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        catch (Exception e){
            logger.error("Error api deleteAccount: {}", e.getMessage());
            return new ResponseEntity<>("Error api deleteAccount: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateClient(@RequestBody Account account) {
        return new ResponseEntity<>(accountService.updateAccount(account), HttpStatus.CREATED);
    }
}
