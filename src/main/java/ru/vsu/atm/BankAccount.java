package ru.vsu.atm;
public abstract class BankAccount {
    public abstract void deposit(long amount);
    public abstract void withdraw(long amount);
    public abstract long getBalance();
    public abstract AccountType getAccountType();
}