package ru.vsu.atm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("DEBIT")
public class DebitAccount extends BankAccount {

    @Override
    public long getAvailableBalance() { return getBalance(); }

    @Override
    public AccountType getAccountType() { return AccountType.DEBIT; }
}
