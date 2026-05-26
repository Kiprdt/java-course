package ru.vsu.atm.controller;

import org.springframework.web.bind.annotation.*;
import ru.vsu.atm.dto.*;
import ru.vsu.atm.model.BankAccount;
import ru.vsu.atm.model.User;
import ru.vsu.atm.service.AccountService;
import ru.vsu.atm.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AccountController {
    private final AccountService accountService;
    private final UserService userService;

    public AccountController(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    @PostMapping("/users/{userId}/accounts")
    public AccountResponse createAccount(@PathVariable Long userId, @RequestBody AccountRequest request) {
        BankAccount account = accountService.openAccount(userId, request.getType());
        return accountService.mapToResponse(account);
    }

    @GetMapping("/users/{userId}/accounts")
    public List<AccountResponse> getAccounts(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        List<AccountResponse> responses = new ArrayList<>();
        for (BankAccount acc : user.getAccounts()) {
            responses.add(accountService.mapToResponse(acc));
        }
        return responses;
    }

    @GetMapping("/accounts/{accountId}")
    public AccountResponse getAccount(@PathVariable Long accountId) {
        BankAccount account = accountService.getAccount(accountId);
        return accountService.mapToResponse(account);
    }

    @PostMapping("/accounts/{accountId}/deposit")
    public AccountResponse deposit(@PathVariable Long accountId, @RequestBody AmountRequest request) {
        BankAccount account = accountService.deposit(accountId, request.getAmount());
        return accountService.mapToResponse(account);
    }

    @PostMapping("/accounts/{accountId}/withdraw")
    public AccountResponse withdraw(@PathVariable Long accountId, @RequestBody AmountRequest request) {
        BankAccount account = accountService.withdraw(accountId, request.getAmount());
        return accountService.mapToResponse(account);
    }

    @GetMapping("/users/{userId}/accounts/summary")
    public SummaryResponse getSummary(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return accountService.getAccountsSummary(userId, user.getAccounts());
    }
}