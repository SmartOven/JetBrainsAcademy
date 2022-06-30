package banking;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        BankManager bankManager = new BankManager(new Bank());

        bankManager.work(console);
    }
}