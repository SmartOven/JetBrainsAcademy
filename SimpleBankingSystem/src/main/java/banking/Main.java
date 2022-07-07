package banking;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);

        String databaseUrl = "jdbc:sqlite:";
        if (args.length != 2) {
//            databaseUrl = "bank_accounts.db";
            databaseUrl += "D:\\DiskApps\\Programming\\GitRepos\\JetBrainsAcademy\\SimpleBankingSystem\\src\\main\\resources\\bank_accounts.db";
        } else {
            databaseUrl += args[1];
        }

        Bank bank = new Bank(databaseUrl);
        BankManager bankManager = new BankManager(bank);

        bankManager.work(console);
    }
}