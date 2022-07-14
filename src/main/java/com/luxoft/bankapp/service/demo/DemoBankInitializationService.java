package com.luxoft.bankapp.service.demo;

import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.Banking;
import com.luxoft.bankapp.service.BankingImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Profile("dev")
public class DemoBankInitializationService implements BankInitializationService {

    @Autowired
    private ApplicationContext context;

    @Override
    @PostConstruct
    public void createClientForDemo() {
        Banking banking = context.getBean(BankingImpl.class);

        Client client_1 = (Client) context.getBean("client1");
        Client client_2 = (Client) context.getBean("client2");

        banking.addClient(client_1);
        banking.addClient(client_2);
    }
}
