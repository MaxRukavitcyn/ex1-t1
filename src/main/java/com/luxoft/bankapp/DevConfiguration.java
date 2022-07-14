package com.luxoft.bankapp;


import com.luxoft.bankapp.model.AbstractAccount;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.SavingAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import static com.luxoft.bankapp.model.Client.Gender.MALE;

@Configuration
@Profile("dev")
@PropertySource("classpath:clients.properties")
public class DevConfiguration {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Environment environment;

    @Bean(name = "checkingAccount2")
    public CheckingAccount getDemoCheckingAccount2()
    {
        return new CheckingAccount(1_500);
    }

    @Bean(name = "client2")
    public Client getDemoClient2()
    {
        String name = environment.getProperty("client2");

        Client client = new Client(name, MALE);
        client.setCity("Kiev");

        AbstractAccount checking = (CheckingAccount)
                applicationContext.getBean("checkingAccount2");

        client.addAccount(checking);

        return client;
    }

    @Bean(name = "savingAccount1")
    public SavingAccount getDemoSavingAccount1() {
        return new SavingAccount(1_000);
    }

    @Bean(name = "checkingAccount1")
    public CheckingAccount getDemoCheckingAccount1()
    {
        return new CheckingAccount(1_000);
    }

    @Bean(name = "client1")
    public Client getDemoClient1()
    {
        String name = environment.getProperty("client1");

        Client client = new Client(name, MALE);
        client.setCity("Moscow");

        AbstractAccount checking = (CheckingAccount)
                applicationContext.getBean("checkingAccount1");

        client.addAccount(checking);

        return client;
    }
}
