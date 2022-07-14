package com.luxoft.bankapp;

import com.luxoft.bankapp.model.AbstractAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.SavingAccount;
import com.luxoft.bankapp.service.Banking;
import com.luxoft.bankapp.service.BankingImpl;
import com.luxoft.bankapp.service.operations.BankingOperationsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BankApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BankApplicationTests3 {
    private static final String[] CLIENT_NAMES =
            {"Jonny Bravo", "Adam Budzinski", "Anna Smith"};

    @Autowired
    private Banking banking;

    @Autowired
    private BankingOperationsService bankingOperationsService;

    @Test
    public void bankingBeanConfiguration() {
        assertNotNull(banking, "banking bean should be configured");
        assertTrue((banking instanceof BankingImpl), "storage should be instantiated with BankingImpl class");
    }

    @Test
    public void deposit() {
        Client client = banking.getClient(CLIENT_NAMES[0]);

        assertEquals(CLIENT_NAMES[0], client.getName());

        double amount = 100;
        double expected = client.getActiveAccount().getBalance() + amount;

        client.deposit(100);
        client = banking.getClient(CLIENT_NAMES[0]);

        assertEquals(expected, client.getActiveAccount().getBalance());
    }

    @Test
    public void withdraw() {
        Client client = banking.getClient(CLIENT_NAMES[0]);

        assertEquals(CLIENT_NAMES[0], client.getName());

        double amount = 100;
        double expected = client.getActiveAccount().getBalance() - amount;

        client.withdraw(100);
        client = banking.getClient(CLIENT_NAMES[0]);

        assertEquals(expected, client.getActiveAccount().getBalance());
    }

    @Test
    public void adminResetBalance() throws Exception {
        Client client = banking.getClient(CLIENT_NAMES[0]);
        AbstractAccount account = client.getAccount(SavingAccount.class);

        bankingOperationsService.adminResetBalance(account);

        TimeUnit.SECONDS.sleep(1);

        client = banking.getClient(CLIENT_NAMES[0]);
        assertEquals(0, client.getAccount(SavingAccount.class).getBalance());
    }
}
