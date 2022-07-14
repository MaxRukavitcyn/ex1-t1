package com.luxoft.bankapp;

import com.luxoft.bankapp.model.AbstractAccount;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.SavingAccount;
import com.luxoft.bankapp.service.Banking;
import com.luxoft.bankapp.service.BankingImpl;
import com.luxoft.bankapp.service.storage.ClientRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.lang.annotation.Annotation;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BankApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BankApplicationTests1 {
    private static final String[] CLIENT_NAMES =
            {"Jonny Bravo", "Adam Budzinski", "Anna Smith"};

    @Autowired
    private Banking banking;

    @Autowired
    private ClientRepository clientRepository;

    @AfterAll
    public void clean() {
        for (Client c : banking.getClients()) {
            banking.deleteClient(c);
        }
    }

    @Test
    public void storageBeanConfiguration() {
        assertNotNull(clientRepository, "storage bean should be configured");
        assertTrue((clientRepository instanceof ClientRepository), "storage should be instantiated with ClientCrudStorage class");
    }

    @Test
    public void bankingBeanConfiguration() {
        assertNotNull(banking, "banking bean should be configured");
        assertTrue((banking instanceof BankingImpl), "storage should be instantiated with BankingImpl class");
    }

    @Test
    public void bankingBeanAnnotation1() {
        Annotation annotation = null;

        try {
            annotation = BankingImpl.class.getDeclaredField("clientRepository")
                    .getAnnotation(Autowired.class);
        } catch (NoSuchFieldException e) {
            fail("BankingImpl should contains clientRepository field");
        }

        assertNotNull(annotation, "clientRepository field should contain annotation @Autowired");
    }

    @Test
    public void bankingBeanAnnotation2() {
        Annotation annotation = null;

        try {
            annotation = BankingImpl.class.getDeclaredField("accountRepository")
                    .getAnnotation(Autowired.class);
        } catch (NoSuchFieldException e) {
            fail("BankingImpl should contains accountRepository field");
        }

        assertNotNull(annotation, "accountRepository field should contain annotation @Autowired");
    }

    @Test
    public void initializationClient1() {
        Client client = banking.getClient(CLIENT_NAMES[0]);
        assertNotNull(client, "banking should have client with name: " + CLIENT_NAMES[0]);

        assertEquals(2, client.getAccounts().size());
    }

    @Test
    public void client1SavingAccount() {
        Client client = banking.getClient(CLIENT_NAMES[0]);

        AbstractAccount account = client.getAccount(SavingAccount.class);

        assertNotNull(account,
                client.getName() + "should have saving account");

        assertEquals(1000, account.getBalance());
    }

    @Test
    public void client1CheckingAccount() {
        Client client = banking.getClient(CLIENT_NAMES[0]);

        CheckingAccount account = (CheckingAccount) client.getAccount(CheckingAccount.class);

        assertNotNull(account,
                client.getName() + "should have checking account");

        assertEquals(4000, account.getBalance());
        assertEquals(1000, account.getOverdraft());
    }

    @Test
    public void initializationClient2() {
        Client client = banking.getClient(CLIENT_NAMES[1]);
        assertNotNull(client, "banking should have client with name: " + CLIENT_NAMES[1]);

        assertEquals(1, client.getAccounts().size());
    }

    @Test
    public void client2CheckingAccount() {
        Client client = banking.getClient(CLIENT_NAMES[1]);

        CheckingAccount account = (CheckingAccount) client.getAccount(CheckingAccount.class);

        assertNotNull(account,
                client.getName() + "should have checking account");

        assertEquals(-500, account.getBalance());
        assertEquals(1500, account.getOverdraft());
    }
}
