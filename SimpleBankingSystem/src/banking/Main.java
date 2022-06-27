package banking;

public class Main {
    public static void main(String[] args) {
        BankManager bankManager = new BankManager(new Bank());

        bankManager.createNewAccount();
    }
}