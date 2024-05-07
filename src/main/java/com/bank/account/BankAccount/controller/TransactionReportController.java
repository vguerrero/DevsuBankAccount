package com.bank.account.BankAccount.controller;

import com.bank.account.BankAccount.model.Account;
import com.bank.account.BankAccount.model.TransactionReportDTO;
import com.bank.account.BankAccount.service.AccountTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("reporte")
public class TransactionReportController {

    @Autowired
    AccountTransactionService accountTransactionService;

    @GetMapping
    public ResponseEntity<List<TransactionReportDTO>> getReport(@RequestParam long clientId, @RequestParam String from,@RequestParam String to) {
        return new ResponseEntity<>(accountTransactionService.getTransactionReport(clientId, from, to), HttpStatus.OK);
    }

}
