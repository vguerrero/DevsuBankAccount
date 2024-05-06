package com.bank.account.BankAccount.service;

import com.bank.account.BankAccount.model.Account;
import com.bank.account.BankAccount.model.ClientMessageDTO;
import com.bank.account.BankAccount.repository.AccountRepository;
import com.bank.account.BankAccount.repository.AccountTransactionRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqReceptor implements RabbitListenerConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMqReceptor.class);

    @Autowired
    AccountService accountService;



    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {

    }
    @RabbitListener(queues = "${spring.rabbitmq.client.queue}")
    @Transactional
    public void receivedMessage(ClientMessageDTO client) {
        try {
            if (client != null && client.getClientId() > 0) {
                logger.info("deleting the client.. {}", client.getClientId());
                accountService.deleteClientTransactions(client.getClientId());
            }
        }
        catch (Exception e){
            logger.error("receivedMessage Error deleting client account: {}", e.getMessage());
        }

    }
}
