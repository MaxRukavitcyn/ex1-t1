package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.OverDraftLimitExceededException;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class CheckingAccount extends AbstractAccount {

    @Column(name = "OVERDRAFT")
    private double overdraft = 0;

    public CheckingAccount() {
    }

    public CheckingAccount(double overdraft) {

        setOverdraft(overdraft);
    }

    public void setOverdraft(double amount) {

        if (overdraft < 0) {
            return;
        }

        overdraft = amount;
    }

    public double getOverdraft() {

        return overdraft;
    }

    @Override
    public void withdraw(double amount) throws OverDraftLimitExceededException {

        if (getBalance() + overdraft < amount) {
            throw new OverDraftLimitExceededException(String.valueOf(getId()), getBalance() + overdraft);
        }

        setBalance(getBalance() - amount);
    }
}
