package com.luxoft.bankapp.service.operations;

import com.luxoft.bankapp.model.AbstractAccount;
import com.luxoft.bankapp.model.Client;

public interface BankingOperationsService {
    double getBalance(Client client);

    double getBalance(AbstractAccount account);

    void deposit(Client client, double amount);

    AbstractAccount deposit(AbstractAccount account, double amount);

    void withdraw(Client client, double amount);

    AbstractAccount withdraw(AbstractAccount account, double amount);

    void adminResetBalance(AbstractAccount account);

}
