package com.luxoft.bankapp;

import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.storage.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BankApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BankApplicationTests2 {
    private static final String[] CLIENT_NAMES =
            {"Jonny Bravo", "Adam Budzinski", "Anna Smith"};

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void storageBeanConfiguration() {
        assertNotNull(clientRepository, "storage bean should be configured");
        assertTrue((clientRepository instanceof ClientRepository), "storage should be instantiated with ClientCrudStorage class");
    }

    @Test
    public void findClientsSortedByName() {
        List<Client> clients = clientRepository.findAll(Sort.by("name"));

        assertEquals(3, clients.size());
        assertEquals(CLIENT_NAMES[1], clients.get(0).getName());
        assertEquals(CLIENT_NAMES[2], clients.get(1).getName());
        assertEquals(CLIENT_NAMES[0], clients.get(2).getName());
    }

    @Test
    public void findClientsSortedByCity() {
        List<Client> clients = clientRepository.findAll(Sort.by("city"));

        assertEquals(CLIENT_NAMES[1], clients.get(0).getName());
        assertEquals(CLIENT_NAMES[0], clients.get(1).getName());
        assertEquals(CLIENT_NAMES[2], clients.get(2).getName());
    }

    @Test
    public void findClientsPage1() {
        List<Client> clients = clientRepository.findAll(PageRequest.of(0, 2)).toList();

        assertEquals(2, clients.size());
        assertEquals(CLIENT_NAMES[1], clients.get(0).getName());
        assertEquals(CLIENT_NAMES[2], clients.get(1).getName());
    }

    @Test
    public void findClientsPage2() {
        List<Client> clients = clientRepository.findAll(PageRequest.of(1, 2)).toList();

        assertEquals(1, clients.size());
        assertEquals(CLIENT_NAMES[0], clients.get(0).getName());
    }
}
