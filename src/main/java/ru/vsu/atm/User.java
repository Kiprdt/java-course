package ru.vsu.atm;
import java.util.ArrayList;
import java.util.List;

public class User {
    private final String login;
    private final String password;
    private final List<BankAccount> accounts;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.accounts = new ArrayList<>();
    }

    public String getLogin() { return login; }
    public String getPassword() { return password; }
    public List<BankAccount> getAccounts() { return accounts; }
}