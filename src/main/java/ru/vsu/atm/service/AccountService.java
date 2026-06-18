package ru.vsu.atm.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.atm.dto.AccountResponse;
import ru.vsu.atm.dto.SummaryResponse;
import ru.vsu.atm.exception.ValidationException;
import ru.vsu.atm.model.*;
import ru.vsu.atm.repository.AccountRepository;

import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserService userService;

    public AccountService(AccountRepository accountRepository, UserService userService) {
        this.accountRepository = accountRepository;
        this.userService = userService;
    }

    public BankAccount openAccount(Long userId, AccountType type) {
        User user = userService.getUserById(userId);
        BankAccount account = (type == AccountType.DEBIT) ? new DebitAccount() : new CreditAccount();
        account.setUser(user);
        return accountRepository.save(account);
    }

    public BankAccount getAccount(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new ValidationException("Счет не найден."));
    }

    public BankAccount deposit(Long accountId, long amount) {
        if (amount <= 0) throw new ValidationException("Сумма должна быть > 0.");
        BankAccount account = getAccount(accountId);
        account.deposit(amount);
        return accountRepository.save(account);
    }

    public BankAccount withdraw(Long accountId, long amount) {
        if (amount <= 0) throw new ValidationException("Сумма должна быть > 0.");
        BankAccount account = getAccount(accountId);
        if (account.getAvailableBalance() < amount) {
            throw new ValidationException("Недостаточно средств.");
        }
        account.withdraw(amount);
        return accountRepository.save(account);
    }

    @Transactional
    public void transfer(Long fromId, Long toId, long amount) {
        withdraw(fromId, amount);
        deposit(toId, amount);
    }

    // Требование из ТЗ (Wildcard PECS)
    public SummaryResponse getAccountsSummary(Long userId, List<? extends BankAccount> accounts) {
        long totalBalance = 0;
        long totalAvailable = 0;
        for (BankAccount acc : accounts) {
            totalBalance += acc.getBalance();
            totalAvailable += acc.getAvailableBalance();
        }
        return new SummaryResponse(userId, totalBalance, totalAvailable, accounts.size());
    }

    // Маппер в DTO
    public AccountResponse mapToResponse(BankAccount acc) {
        Long creditLine = (acc instanceof CreditAccount) ? ((CreditAccount) acc).getCreditLine() : null;
        return new AccountResponse(acc.getId(), acc.getAccountType(), acc.getBalance(), creditLine, acc.getAvailableBalance());
    }
}