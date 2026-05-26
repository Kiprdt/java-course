package ru.vsu.atm;
public class DebitAccount extends BankAccount {
    private long balance = 0;

    @Override
    public void deposit(long amount) {
        this.balance += amount;
    }

    @Override
    public void withdraw(long amount) {
        this.balance -= amount;
    }

    @Override
    public long getBalance() {
        return balance;
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.DEBIT;
    }
}