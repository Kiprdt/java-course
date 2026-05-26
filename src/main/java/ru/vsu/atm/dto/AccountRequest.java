package ru.vsu.atm.dto;
import ru.vsu.atm.model.AccountType;

public class AccountRequest {
    private AccountType type;
    public AccountType getType() { return type; }
    public void setType(AccountType type) { this.type = type; }
}