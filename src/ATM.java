import java.util.Scanner;

public class ATM {
    final private BankAccount account;
//    private BankAccount[] allAccounts;
    final private Scanner scanner;

    public ATM(BankAccount account) {
        this.account = account;
//        this.allAccounts = allAccounts;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;
        while (running) {
            showMenu();
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Your balance: " + account.getBalance());
                    break;
                case 2:
                    depositMoney();
                    break;
                case 3:
                    withdrawMoney();
                    break;
//                case 4:
//                    transferMoney();
//                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void showMenu() {
        System.out.println("\n1. Check balance");
        System.out.println("2. Deposit money");
        System.out.println("3. Withdraw money");
//        System.out.println("4. Transfer money");
        System.out.println("4. Exit");
        System.out.print("> ");
    }

    private void depositMoney() {
        System.out.println("Введите сумму:");
        long amount = scanner.nextLong();
        if (amount > 0) {
            account.deposit(amount);
            System.out.println("Внесено:" + amount + "\n" + "Текущий баланс: " + account.getBalance());

        } else {
            System.out.println("Invalid amount");
        }
    }

    private void withdrawMoney() {
        System.out.println("Введите сумму:");
        long amount = scanner.nextLong();
        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }
        if (account.withdraw(amount)) {
            dispenseMoney(amount);
            System.out.println("Снято:" + amount + "\n" + "Текущий баланс: " + account.getBalance());
        } else {
            System.out.println("Not enough money");
        }
    }
    private void dispenseMoney(long amount) {
        long current = amount;
        for (Denomination bill : Denomination.values()) {
            long count = current / bill.getValue();
            if (count > 0) {
                System.out.println(bill.getValue() + " x " + count);
                current %= bill.getValue();
            }
        }
    }

//    private void transferMoney() {
//        System.out.println("Введите Имя получателя");
//        String targetName = scanner.next();
//        BankAccount targetAccount = null;
//        for (BankAccount acc : allAccounts) {
//            if (acc.getOwnerName().equals(targetName)) {
//                targetAccount = acc;
//                break;
//            }
//        }
//        System.out.println("Введите сумму перевода:");
//        long amount = scanner.nextLong();
//
//        if (account.withdraw(amount)) {
//            targetAccount.deposit(amount);
//            System.out.println("Перевод выполнен успешно.");
//        } else {
//            System.out.println("Недостаточно средств для перевода.");
//        }
//
//    }
}
