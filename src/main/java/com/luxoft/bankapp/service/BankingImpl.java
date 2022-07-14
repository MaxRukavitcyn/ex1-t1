package com.luxoft.bankapp.service;

import com.luxoft.bankapp.exceptions.AccountNotFoundException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.service.storage.AccountRepository;
import com.luxoft.bankapp.service.storage.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class BankingImpl implements Banking {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Client addClient(Client client) {

        return clientRepository.save(client);
    }

    @Override
    public Client getClient(String name) {

        return clientRepository
                .findByName(name)
                .orElseThrow(() -> new ClientNotFoundException(name));
    }

    @Override
    public List<Client> getClients()
    {
        return StreamSupport
                .stream(clientRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteClient(Client client) {

        clientRepository.deleteById(client.getId());
    }

    @Override
    public AbstractAccount createAccount(Client c, Class type) {

        Client client = clientRepository
                .findById(c.getId())
                .orElseThrow(() ->
                        new ClientNotFoundException(c.getId()));

        AbstractAccount account = new SavingAccount(0);

        if (type == CheckingAccount.class) {
            account = new CheckingAccount(0);
        }

        account = accountRepository.save(account);
        client.addAccount(account);

        clientRepository.save(client);

        return account;
    }



    @Override
    public AbstractAccount getAccount(Client client, Class type) {

        return client.getAccounts().stream()
                .filter(a -> a.getClass() == type)
                .findFirst()
                .orElseThrow(() -> new AccountNotFoundException());
    }


    @Override
    public List<AbstractAccount> getAllAccounts() {
        return StreamSupport
                .stream(accountRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<AbstractAccount> getAllAccounts(Client client) {

        return clientRepository
                .findById(client.getId())
                .map(client1 -> client1.getAccounts())
                .orElseGet(null);
    }

    @Override
    public void transferMoney(Client from, Client to, double amount) {
        from.withdraw(amount);
        to.deposit(amount);
    }
}
