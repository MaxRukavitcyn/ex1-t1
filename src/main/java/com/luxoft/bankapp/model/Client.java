package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.AccountNumberLimitException;
import com.luxoft.bankapp.exceptions.ActiveAccountNotSet;
import com.luxoft.bankapp.service.storage.ClientRepository;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity
@Table(name = "CLIENT")
public class Client {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @Column(name = "NAME")
    private String name;

    @OneToMany(targetEntity = AbstractAccount.class, fetch = EAGER)
    private List<AbstractAccount> accounts = new ArrayList<>(2);

    @OneToOne
    private AbstractAccount activeAccount;

    @Column(name = "GENDER")
    private Gender gender;

    @Column(name = "CITY")
    private String city;

    public Client() {
    }

    public Client(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
    }

    public synchronized double getBalance() {

        if (!checkIfActiveAccountSet()) {

            throw new ActiveAccountNotSet(name);
        }

        return activeAccount.getBalance();
    }

    public synchronized void deposit(double amount) {

    }

    public synchronized void withdraw(double amount) {

    }

    private boolean checkIfActiveAccountSet() {

        return activeAccount != null;
    }

    public void removeAccount(Class type) {

        accounts = accounts.stream()
                .filter(a -> a.getClass() != type)
                .collect(Collectors.toList());

    }

    public void setAccounts(Set<AbstractAccount> accounts) {

        this.accounts.clear();
        this.accounts.addAll(accounts);

    }

    public List<AbstractAccount> getAccounts() {

        return Collections.unmodifiableList(accounts);
    }

    public AbstractAccount getAccount(Class type) {

        for (AbstractAccount account : accounts) {

            if (account.getClass().equals(type)) {
                return account;
            }
        }

        return null;
    }

    public void addAccount(AbstractAccount account) throws AccountNumberLimitException {

        if (accounts.size() >= 3) {

            throw new AccountNumberLimitException();
        }

        if (account != null) {

            accounts.add(account);
        }
    }

    public void setDefaultActiveAccountIfNotSet() {

        if (activeAccount == null && accounts != null && !accounts.isEmpty()) {

            AbstractAccount account = getAccount(CheckingAccount.class);

            if (account == null) {

                account = accounts.iterator().next();
            }

            activeAccount = account;

            System.out.println("Default account set for " + name + ":"
                    + activeAccount.getClass().getSimpleName());
        }
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        return id == client.id &&
                Objects.equals(name, client.name) &&
                Objects.equals(city, client.city);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, city);
    }

    private StringBuilder getSimpleInfoBuilder() {
        StringBuilder builder = new StringBuilder();

        builder.append("\nClient: ")
                .append(name)
                .append("\nGender: ")
                .append(getGender());

        return builder;
    }

    @Override
    public String toString() {

        StringBuilder builder = getSimpleInfoBuilder();

        builder.append("\nAccounts:");

        for (AbstractAccount account : accounts) {
            builder.append(account.toString());
        }

        builder.append("\nActive account: ");

        builder.append(checkIfActiveAccountSet() ?
                activeAccount.getClass().getSimpleName() : "not set");

        return builder.toString();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AbstractAccount getActiveAccount() {
        return activeAccount;
    }

    public void setActiveAccount(AbstractAccount activeAccount) {
        this.activeAccount = activeAccount;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public enum Gender {

        MALE("Mr"), FEMALE("Ms"), UNDEFINED("");

        private String prefix;

        String getSalutation() {
            return prefix;
        }

        Gender(String prefix) {
            this.prefix = prefix;
        }
    }

}
