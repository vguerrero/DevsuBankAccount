package com.bank.account.BankAccount.controller;


import com.bank.account.BankAccount.model.Account;
import com.bank.account.BankAccount.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cuentas")
public class AccountController {


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
}
