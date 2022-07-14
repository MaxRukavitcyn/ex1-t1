package com.luxoft.bankapp.exceptions;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException() {
        super();
    }

    public AccountNotFoundException(long id) {
        super("Account with id: " + id + " not found.");
    }
}
