package com.bank.account.BankAccount.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.io.Serializable;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = ClientMessageDTO.class)
public class ClientMessageDTO implements Serializable {
    private long clientId;
    private String name;

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ClientMessageDTO{" +
                "clientId=" + clientId +
                ", name='" + name + '\'' +
                '}';
    }
}
