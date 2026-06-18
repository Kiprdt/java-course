package ru.vsu.atm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("CREDIT")
public class CreditAccount extends BankAccount {
    private long creditLine = 20000;

    @Override
    public long getAvailableBalance() { return getBalance() + creditLine; }

    public long getCreditLine() { return creditLine; }

    @Override
    public AccountType getAccountType() { return AccountType.CREDIT; }
}
