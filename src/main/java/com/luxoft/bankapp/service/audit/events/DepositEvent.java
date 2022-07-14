package com.luxoft.bankapp.service.audit.events;

import lombok.Getter;

@Getter
public class DepositEvent extends AccountEvent {
    private double amount;

    public DepositEvent(long accountId, double amount) {

        super("DEPOSIT", accountId);
        this.amount = amount;
    }

}
