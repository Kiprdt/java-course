package ru.vsu.atm;

import org.springframework.stereotype.Component;
import java.util.Scanner;

@Component
public class AtmConsole {
    private final UserService userService;
    private final AccountService accountService;
    private final Scanner scanner;
    private User currentUser;

    public AtmConsole(UserService userService, AccountService accountService, Scanner scanner) {
        this.userService = userService;
        this.accountService = accountService;
        this.scanner = scanner;
    }

    public void start() {
        while (true) {
            try {
                if (currentUser == null) {
                    showAuthMenu();
                } else {
                    showMainMenu();
                }
            } catch (AtmException e) {
                System.out.println("Ошибка: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Ввод неверного формата. Попробуйте еще раз.");
                scanner.nextLine();
            }
        }
    }

    private void showAuthMenu() {
        System.out.println("\n1. Sign in (Login)");
        System.out.println("2. Sign up (Register)");
        System.out.print("> ");
        int choice = scanner.nextInt();

        System.out.print("Логин: ");
        String login = scanner.next();
        System.out.print("Пароль: ");
        String password = scanner.next();

        if (choice == 1) {
            currentUser = userService.login(login, password);
            System.out.println("Успешный вход.");
        } else if (choice == 2) {
            currentUser = userService.register(login, password);
            System.out.println("Регистрация успешна.");
        } else {
            System.out.println("Неверный выбор.");
        }
    }

    private void showMainMenu() {
        System.out.println("\n1. Проверить баланс");
        System.out.println("2. Завести счет");
        System.out.println("3. Пополнить счет");
        System.out.println("4. Снять деньги");
        System.out.println("5. Перевод между счетами");
        System.out.println("6. Выйти (Logout)");
        System.out.print("> ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> {
                long total = accountService.getBalance(currentUser.getAccounts());
                System.out.println("Общий доступный баланс: " + total);
            }
            case 2 -> openAccount();
            case 3 -> depositMoney();
            case 4 -> withdrawMoney();
            case 5 -> transferMoney();
            case 6 -> currentUser = null;
            default -> System.out.println("Неверный выбор.");
        }
    }

    private void openAccount() {
        System.out.println("1. Дебетовый (DEBIT)");
        System.out.println("2. Кредитный (CREDIT)");
        System.out.print("> ");
        int typeChoice = scanner.nextInt();
        if (typeChoice == 1) {
            accountService.open(currentUser, AccountType.DEBIT);
        } else if (typeChoice == 2) {
            accountService.open(currentUser, AccountType.CREDIT);
        } else {
            throw new ValidationException("Неверный тип счета.");
        }
        System.out.println("Счет успешно открыт.");
    }

    private void depositMoney() {
        BankAccount account = selectAccount();
        System.out.print("Введите сумму: ");
        long amount = scanner.nextLong();
        accountService.deposit(account, amount);
        System.out.println("Счет пополнен.");
    }

    private void withdrawMoney() {
        BankAccount account = selectAccount();
        System.out.print("Введите сумму: ");
        long amount = scanner.nextLong();
        accountService.withdraw(account, amount);
        System.out.println("Деньги выданы.");
    }

    private void transferMoney() {
        System.out.println("Откуда переводим:");
        BankAccount from = selectAccount();
        System.out.println("Куда переводим:");
        BankAccount to = selectAccount();

        if (from == to) {
            throw new ValidationException("Нельзя перевести на тот же самый счет.");
        }

        System.out.print("Введите сумму перевода: ");
        long amount = scanner.nextLong();
        accountService.transfer(from, to, amount);
        System.out.println("Перевод успешен.");
    }

    private BankAccount selectAccount() {
        if (currentUser.getAccounts().isEmpty()) {
            throw new ValidationException("У вас нет открытых счетов. Сначала откройте счет.");
        }
        accountService.getBalance(currentUser.getAccounts());
        System.out.print("Выберите номер счета: ");
        int index = scanner.nextInt() - 1;
        if (index < 0 || index >= currentUser.getAccounts().size()) {
            throw new ValidationException("Неверный номер счета.");
        }
        return currentUser.getAccounts().get(index);
    }
}