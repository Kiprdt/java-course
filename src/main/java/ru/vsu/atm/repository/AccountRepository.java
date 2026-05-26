package ru.vsu.atm.repository;

import org.springframework.stereotype.Repository;
import ru.vsu.atm.model.BankAccount;
import java.util.HashMap;
import java.util.Map;

@Repository
public class AccountRepository {
    private final Map<Long, BankAccount> accounts = new HashMap<>();
    private long idCounter = 10;

    public BankAccount save(BankAccount account) {
        account.setId(idCounter++);
        accounts.put(account.getId(), account);
        return account;
    }

    public BankAccount findById(Long id) {
        return accounts.get(id);
    }
}