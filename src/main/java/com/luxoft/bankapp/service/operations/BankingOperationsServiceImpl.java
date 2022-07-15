package com.luxoft.bankapp.service.operations;

import com.luxoft.bankapp.exceptions.AccountNotFoundException;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.model.AbstractAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.storage.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BankingOperationsServiceImpl implements BankingOperationsService {

    @Autowired
    private AccountRepository accountRepository;


    @Override
    public double getBalance(Client client) {
        return client.getBalance();
    }

    @Override
    public double getBalance(AbstractAccount account) {
        return accountRepository
                .getById(account.getId())
                .map(AbstractAccount::getId)
                .orElseThrow(() -> new AccountNotFoundException(account.getId()));
    }

    @Override
    public void deposit(Client client, double amount) {
        client.deposit(amount);
    }

    @Override
    @Transactional
    public AbstractAccount deposit(AbstractAccount account, double amount) {

        AbstractAccount foundAccount = accountRepository
                .getById(account.getId())
                .orElseThrow(() -> new AccountNotFoundException(account.getId()));

        foundAccount.deposit(amount);

        return accountRepository.save(foundAccount);
    }

    @Override
    public void withdraw(Client client, double amount) {
        client.withdraw(amount);
    }

    @Override
    @Transactional
    public AbstractAccount withdraw(AbstractAccount account, double amount) {

        AbstractAccount foundAccount = accountRepository
                .getById(account.getId())
                .orElseThrow(() -> new AccountNotFoundException(account.getId()));

        if (foundAccount.getBalance() < amount) {
            throw new NotEnoughFundsException(amount);
        }

        foundAccount.withdraw(amount);

        return accountRepository.save(foundAccount);
    }

    @Override
    public void adminResetBalance(AbstractAccount account) {
        accountRepository.getAccount(account.getId())
                .thenAcceptAsync(a -> accountRepository.updateResetBalance(a.getId(), 0.0));
    }
}
