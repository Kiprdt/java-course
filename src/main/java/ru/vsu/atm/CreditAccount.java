package ru.vsu.atm;
public class CreditAccount extends BankAccount {
    private long creditLine = 20000;

    @Override
    public void deposit(long amount) {
        this.creditLine += amount;
    }

    @Override
    public void withdraw(long amount) {
        this.creditLine -= amount;
    }

    @Override
    public long getBalance() {
        return creditLine;
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.CREDIT;
    }
}