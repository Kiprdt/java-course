public class Main {
    public static void main(String[] args) {
        BankAccount user1 = new BankAccount("John", 1000);
//        BankAccount user2 = new BankAccount("Alice", 500);

//        BankAccount[] database = {user1, user2};

        ATM atm = new ATM(user1);
        atm.start();
    }
}