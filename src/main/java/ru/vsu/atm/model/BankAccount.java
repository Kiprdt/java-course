package ru.vsu.atm.model;

public abstract class BankAccount {
    private Long id;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public abstract void deposit(long amount);
    public abstract void withdraw(long amount);
    public abstract long getBalance();
    public abstract long getAvailableBalance();
    public abstract AccountType getAccountType();
}