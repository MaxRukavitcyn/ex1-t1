package com.luxoft.bankapp.exceptions;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(String name) {
        super("Client " + name + " not found.");
    }

    public ClientNotFoundException(Long id) {
        super("Client with id: " + id + " not found.");
    }
}
