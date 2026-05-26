package ru.vsu.atm;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AccountService {

    public void open(User user, AccountType type) {
        if (type == AccountType.DEBIT) {
            user.getAccounts().add(new DebitAccount());
        } else if (type == AccountType.CREDIT) {
            user.getAccounts().add(new CreditAccount());
        }
    }

    public void deposit(BankAccount account, long amount) {
        if (amount <= 0) {
            throw new ValidationException("Сумма должна быть больше нуля.");
        }
        account.deposit(amount);
    }

    public void withdraw(BankAccount account, long amount) {
        if (amount <= 0) {
            throw new ValidationException("Сумма должна быть больше нуля.");
        }
        if (account.getBalance() < amount) {
            throw new ValidationException("Недостаточно средств на счете.");
        }
        account.withdraw(amount);
    }

    public void transfer(BankAccount from, BankAccount to, long amount) {
        withdraw(from, amount);
        deposit(to, amount);
    }

    public long getBalance(List<? extends BankAccount> accounts) {
        long totalBalance = 0;
        System.out.println("--- Ваши счета ---");
        for (int i = 0; i < accounts.size(); i++) {
            BankAccount acc = accounts.get(i);
            System.out.println((i + 1) + ". " + acc.getAccountType() + " | Баланс: " + acc.getBalance());
            totalBalance += acc.getBalance();
        }
        System.out.println("------------------");
        return totalBalance;
    }
}