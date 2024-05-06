package com.bank.account.BankAccount.service;

import com.bank.account.BankAccount.model.*;
import com.bank.account.BankAccount.repository.AccountTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountTransactionServiceImpl implements AccountTransactionService {

    private static final Logger logger = LoggerFactory.getLogger(AccountTransactionServiceImpl.class);

    @Autowired
    AccountService accountService;

    @Autowired
    private AccountTransactionRepository accountTransactionRepository;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public AccountTransaction createAccountTransaction(AccountTransaction at) throws Exception {
        AccountTransaction response = null;
        if (at != null && !at.getAccountNumber().isEmpty()) {
            at.setDate(new Date());
            Account account = accountService.getAccount(at.getAccountNumber());
            if (account != null) {

                double newBalance;
                at.setValue(Math.abs(at.getValue()));
                if (at.getTransactionType().equals(TransactionType.RETIRO)) {
                    //validate account balance
                    if (account.getBalance() <= at.getValue()) {
                        throw new Exception("Saldo no disponible ");
                    } else {
                        newBalance = account.getBalance() - at.getValue();
                        account.setBalance(newBalance);
                        at.setBalance(newBalance);
                        response = accountTransactionRepository.save(at);
                        accountService.updateAccount(account);
                    }
                } else {
                    newBalance = account.getBalance() + at.getValue();
                    account.setBalance(newBalance);
                    at.setBalance(newBalance);
                    response = accountTransactionRepository.save(at);
                    accountService.updateAccount(account);
                }
            }

        }
        return response;
    }

    @Override
    public void deleteAccountTransaction(long accTransactionId) {
        AccountTransaction t = accountTransactionRepository.getById(accTransactionId);
        if (t != null)
            accountTransactionRepository.delete(t);
    }

    @Override
    public AccountTransaction updateAccountTransaction(AccountTransaction c) {
        return null;
    }

    @Override
    public List<AccountTransaction> getAll() {
        return accountTransactionRepository.findAll();
    }

    /***
     * get getTransactionReport and complete it by rpc call
     * @param clientId
     * @param dateRange
     * @return
     */
    @Override
    public List<TransactionReportDTO> getTransactionReport(long clientId, String dateRange) {
        List<TransactionReportDTO> result = new ArrayList<>();
        try {
            logger.info("getTransactionReport: " + clientId);
            Map<String, String> map = new HashMap<>();
            map.put("id", String.valueOf(clientId));
            String clientName = restTemplate.getForObject(
                    "http://bankclient-app-1:8081/clientes/namebyid?id={id}", String.class, map);
            logger.info("getTransactionReport clientname: " + clientName);
            if (clientName != null && !clientName.isEmpty()) {
                var accountTransactions = accountTransactionRepository.findAccountTransactionsByClientId(clientId);
                result = accountTransactions.stream().map(x ->
                        {
                            return new TransactionReportDTO(clientName, x.getAccountNumber(), x.getDate(), x.getTransactionType().name(), x.getValue(), x.getBalance());
                        }
                ).collect(Collectors.toList());
            }

        } catch (HttpClientErrorException e) {
             logger.error("Client doesn't exist");
        }
        return result;
    }
}
