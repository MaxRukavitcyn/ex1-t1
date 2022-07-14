package com.luxoft.bankapp.service.operations;

import com.luxoft.bankapp.model.AbstractAccount;
import com.luxoft.bankapp.model.Client;
import org.springframework.stereotype.Service;

@Service
public class BankingOperationsServiceImpl implements BankingOperationsService {
    @Override
    public double getBalance(Client client) {
        return client.getBalance();
    }

    @Override
    public double getBalance(AbstractAccount account) {
        return 0.0;
    }

    @Override
    public void deposit(Client client, double amount) {
        client.deposit(amount);
    }

    @Override
    public AbstractAccount deposit(AbstractAccount account, double amount) {
        return null;
    }

    @Override
    public void withdraw(Client client, double amount) {
        client.withdraw(amount);
    }

    @Override
    public AbstractAccount withdraw(AbstractAccount account, double amount) {
        return null;
    }

    @Override
    public void adminResetBalance(AbstractAccount account) {
    }
}
