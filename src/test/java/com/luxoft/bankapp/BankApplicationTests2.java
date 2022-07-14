package com.luxoft.bankapp;

import com.luxoft.bankapp.BankApplication;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.model.AbstractAccount;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.Banking;
import com.luxoft.bankapp.service.operations.BankingOperationsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = BankApplication.class)
public class BankApplicationTests2 {
    @Autowired
    private Banking banking;

    @Autowired
    private BankingOperationsService bankingOperationsService;

    private Client client;

    @BeforeEach
    public void init() {
        client = banking.getClient("Jonny Bravo");
        client.setDefaultActiveAccountIfNotSet();
        client.getActiveAccount().setId(999);
    }

    @Test
    public void depositToClient1() {
        AbstractAccount account = client.getActiveAccount();
        double balance = account.getBalance();
        double amount = 100;

        bankingOperationsService.deposit(client, amount);

        assertEquals(account.getBalance(), balance + amount);
    }

    @Test
    public void depositToClient2() {
        AbstractAccount account = client.getActiveAccount();
        double balance = account.getBalance();
        double amount = 100;

        bankingOperationsService.deposit(account, amount);

        assertEquals(account.getBalance(), balance + amount);
    }

    @Test
    public void getClientBalance() {
        AbstractAccount account = client.getActiveAccount();

        double balance = bankingOperationsService.getBalance(client);

        assertEquals(balance, account.getBalance());
    }

    @Test
    public void withdrawFromClient1() {
        AbstractAccount account = client.getActiveAccount();
        double balance = account.getBalance();
        double amount = 100;


        bankingOperationsService.withdraw(client, amount);

        assertEquals(account.getBalance(), balance - amount);
    }

    @Test
    public void withdrawFromClient2() {
        AbstractAccount account = client.getActiveAccount();
        double balance = account.getBalance();
        double amount = 100;


        bankingOperationsService.withdraw(account, amount);

        assertEquals(account.getBalance(), balance - amount);
    }

    @Test
    public void withdrawFromClient3() {
        AbstractAccount account = client.getActiveAccount();
        double balance = account.getBalance();
        double overdraft = 0;

        if (account instanceof CheckingAccount) {
            overdraft = ((CheckingAccount) account).getOverdraft();
        }

        double amount = balance + overdraft + 1000;

        assertThrows(NotEnoughFundsException.class,
                () -> bankingOperationsService.withdraw(account, amount));

        assertEquals(account.getBalance(), balance);
    }

}
