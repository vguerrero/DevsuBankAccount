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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountTransactionServiceImpl implements AccountTransactionService {

    private static final Logger logger = LoggerFactory.getLogger(AccountTransactionServiceImpl.class);

    AccountService accountService;

    AccountTransactionRepository accountTransactionRepository;

    @Autowired
    private RestTemplate restTemplate;


    public AccountTransactionServiceImpl(AccountService accountService, AccountTransactionRepository accountTransactionRepository) {
        this.accountService = accountService;
        this.accountTransactionRepository = accountTransactionRepository;
    }

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
    public List<TransactionReportDTO> getTransactionReport(long clientId, String from, String to) {
        List<TransactionReportDTO> result = new ArrayList<>();
        try {
            logger.info("getTransactionReport: " + clientId);
            Map<String, String> map = new HashMap<>();
            map.put("id", String.valueOf(clientId));
            String clientName = restTemplate.getForObject(
                    "http://bankclient-app-1:8081/clientes/namebyid?id={id}", String.class, map);
            logger.info("getTransactionReport clientname: " + clientName);
            if (clientName != null && !clientName.isEmpty() && !from.isEmpty() && !to.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date fromDate = sdf.parse(from);
                Date toDate = sdf.parse(to);
                var accountTransactions = accountTransactionRepository.findAccountTransactionsByClientId(clientId, fromDate, toDate);
                result = accountTransactions.stream().map(x ->
                        {
                            return new TransactionReportDTO(clientName, x.getAccountNumber(), x.getDate(), x.getTransactionType().name(), x.getValue(), x.getBalance());
                        }
                ).collect(Collectors.toList());
            }

        } catch (HttpClientErrorException e) {
             logger.error("Client doesn't exist");
        } catch (ParseException e) {
            logger.error("DateFormat error when getTransactionReport.");
            throw new RuntimeException(e);
        }
        return result;
    }
}
