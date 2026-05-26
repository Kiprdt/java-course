package ru.vsu.atm.model;

public class CreditAccount extends BankAccount {
    private long balance = 0;
    private long creditLine = 20000;

    @Override
    public void deposit(long amount) { this.balance += amount; }

    @Override
    public void withdraw(long amount) { this.balance -= amount; }

    @Override
    public long getBalance() { return balance; }

    @Override
    public long getAvailableBalance() { return balance + creditLine; }

    public long getCreditLine() { return creditLine; }

    @Override
    public AccountType getAccountType() { return AccountType.CREDIT; }
}